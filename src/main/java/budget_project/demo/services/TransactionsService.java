package budget_project.demo.services;

import budget_project.demo.dtos.TransactionsRequestDTO;
import budget_project.demo.dtos.TransactionsResponseDTO;
import budget_project.demo.dtos.UpdateTransactionDTO;
import budget_project.demo.dtos.UpdateTransactionResponse;
import budget_project.demo.miscellaneous.SimpleMessage;

import java.util.List;
import java.util.UUID;

public interface TransactionsService {

    TransactionsResponseDTO createTransaction (UUID userId, TransactionsRequestDTO transactionsRequest);
    List<TransactionsResponseDTO> getUserTransactions(UUID userId);
    List<TransactionsResponseDTO> getCategoryTransactions(UUID userId, UUID categoryId);
    UpdateTransactionResponse updateTransaction(UUID transactionId, UUID userId, UpdateTransactionDTO update);
    SimpleMessage deleteTransaction(UUID userId, UUID transactionId);

}
