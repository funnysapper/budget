package budget_project.demo.serviceImpls;

import budget_project.demo.dtos.TransactionsRequestDTO;
import budget_project.demo.dtos.TransactionsResponseDTO;
import budget_project.demo.dtos.UpdateTransactionDTO;
import budget_project.demo.dtos.UpdateTransactionResponse;
import budget_project.demo.entities.Categories;
import budget_project.demo.entities.Transactions;
import budget_project.demo.exceptions.CategoryNotFoundException;
import budget_project.demo.exceptions.ResourceNotFoundException;
import budget_project.demo.exceptions.TransactionNotFoundException;
import budget_project.demo.exceptions.UnauthorizedException;
import budget_project.demo.miscellaneous.SimpleMessage;
import budget_project.demo.repositories.CategoriesRepo;
import budget_project.demo.repositories.TransactionsRepo;
import budget_project.demo.services.TransactionsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TransactionsServiceImpl implements TransactionsService {

    private final CategoriesRepo categoriesRepository;
    private final TransactionsRepo transactionsRepository;

    @Override
    public TransactionsResponseDTO createTransaction(UUID userId, TransactionsRequestDTO transactionsRequest){
          Categories category = categoriesRepository.findById(transactionsRequest.getCategoryId())
                  .orElseThrow(()-> new CategoryNotFoundException("Category not found!"));
          if(!category.getUserId().equals(userId)){
              throw new UnauthorizedException("You do not own this category!");
          }
          Transactions transaction = new Transactions();
          if(transactionsRequest.getTransactionName()==null){
              throw new ResourceNotFoundException("A transaction name is required to create a transaction!");
          }else{transaction.setTransactionName(transactionsRequest.getTransactionName());}
          transaction.setCreatedAt(transactionsRequest.getDate());
          if(transactionsRequest.getAmount()==null){
              throw new ResourceNotFoundException("An amount is required to create a transaction!");
          }else{transaction.setAmount(transactionsRequest.getAmount());}
          transaction.setUserId(userId);
          if(transactionsRequest.getType()== null){
              throw new ResourceNotFoundException("The type of transaction is required!");
          }else{transaction.setType(transactionsRequest.getType());}
          if(StringUtils.hasText(transactionsRequest.getDescription())) {
              transaction.setDescription(transactionsRequest.getDescription());
          }

          Transactions newTransaction = transactionsRepository.save(transaction);
          TransactionsResponseDTO.TransactionsResponseDTOBuilder responseBuilder = TransactionsResponseDTO.builder()
                  .transactionId(newTransaction.getTransactionId())
                  .transactionName(newTransaction.getTransactionName())
                  .amount(newTransaction.getAmount())
                  .categoryId(newTransaction.getCategoryId())
                  .createdAt(newTransaction.getCreatedAt());
                 if(StringUtils.hasText(newTransaction.getDescription())){
                     responseBuilder.description(newTransaction.getDescription());
                 }
                 return responseBuilder.build();

    }

    @Override
    public List<TransactionsResponseDTO> getUserTransactions(UUID userId){
        List<Transactions> userTransactions = transactionsRepository.findByUserId(userId);
        return userTransactions.stream()
                .map(transaction-> {
                    TransactionsResponseDTO.TransactionsResponseDTOBuilder response = TransactionsResponseDTO.builder()
                            .transactionId(transaction.getTransactionId())
                            .amount(transaction.getAmount())
                            .transactionName(transaction.getTransactionName())
                            .categoryId(transaction.getCategoryId())
                            .createdAt(transaction.getCreatedAt());
                    if(StringUtils.hasText(transaction.getDescription())){
                        response.description(transaction.getDescription());
                    }
                    return response.build();
                })
                .collect(Collectors.toList());
    }

    @Override
    public SimpleMessage deleteTransaction(UUID userId, UUID transactionId) {
        Transactions transaction = transactionsRepository.findById(transactionId)
                .orElseThrow(() -> new TransactionNotFoundException("Transaction not found!"));

        if (!transaction.getUserId().equals(userId)) {
            throw new UnauthorizedException("You do not own this transaction.");
        }

        transactionsRepository.deleteById(transactionId);
        return new SimpleMessage("Transaction deleted successfully");
    }

    @Override
    @Transactional
    public UpdateTransactionResponse updateTransaction(UUID transactionId, UUID userId, UpdateTransactionDTO update) {
        Transactions tx = transactionsRepository.findById(transactionId)
                .orElseThrow(() -> new TransactionNotFoundException("Transaction not found"));

        if (!tx.getUserId().equals(userId)) {
            throw new UnauthorizedException("You cannot update this transaction");
        }

        UUID newCategoryId = update.getNewCategoryId();
        if (newCategoryId != null && !newCategoryId.equals(tx.getCategoryId())) {
            Categories newCategory = categoriesRepository.findById(newCategoryId)
                    .orElseThrow(() -> new CategoryNotFoundException("Target category not found"));
            if (!newCategory.getUserId().equals(userId)) {
                throw new UnauthorizedException("You do not own the target category");
            }
            tx.setCategoryId(newCategoryId);
        }

        if (StringUtils.hasText(update.getTransactionName())) {
            tx.setTransactionName(update.getTransactionName());
        }
        if (update.getAmount() != null) {
            tx.setAmount(update.getAmount());
        }
        if (update.getType() != null) {
            tx.setType(update.getType());
        }
        if (StringUtils.hasText(update.getDescription())) {
            tx.setDescription(update.getDescription());
        }

        Transactions updated = transactionsRepository.save(tx);

        UpdateTransactionResponse.UpdateTransactionResponseBuilder response = UpdateTransactionResponse.builder()
                .transactionId(updated.getTransactionId())
                .amount(updated.getAmount())
                .transactionName(updated.getTransactionName())
                .newCategoryId(updated.getCategoryId())
                .lastUpdated(updated.getLastUpdated())
                .type(updated.getType());
        if (StringUtils.hasText(updated.getDescription())) response.description(updated.getDescription());
        return response.build();
    }

    @Override
    public List<TransactionsResponseDTO> getCategoryTransactions(UUID userId, UUID categoryId) {
        Categories category = categoriesRepository.findById(categoryId)
                .orElseThrow(() -> new CategoryNotFoundException("Category not found"));

        if (!category.getUserId().equals(userId)) {
            throw new UnauthorizedException("You do not own this category");
        }

        List<Transactions> transactions = transactionsRepository.findByCategoryId(categoryId);

        return transactions.stream()
                .map(tx -> TransactionsResponseDTO.builder()
                        .transactionId(tx.getTransactionId())
                        .amount(tx.getAmount())
                        .type(tx.getType())
                        .createdAt(tx.getCreatedAt())
                        .description(tx.getDescription())
                        .categoryId(tx.getCategoryId())
                        .build())
                .collect(Collectors.toList());
    }
    
}
