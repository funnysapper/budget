package budget_project.demo.controllers;

import budget_project.demo.dtos.TransactionsRequestDTO;
import budget_project.demo.dtos.TransactionsResponseDTO;
import budget_project.demo.dtos.UpdateTransactionDTO;
import budget_project.demo.dtos.UpdateTransactionResponse;
import budget_project.demo.miscellaneous.CustomUserDetails;
import budget_project.demo.miscellaneous.JwtUtils;
import budget_project.demo.miscellaneous.SimpleMessage;
import budget_project.demo.serviceImpls.TransactionsServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/transactions")
@RequiredArgsConstructor
public class TransactionsController {

    private final TransactionsServiceImpl transactionsService;

    @PostMapping("/create")
    public ResponseEntity<TransactionsResponseDTO> createTransaction(@RequestBody TransactionsRequestDTO request){
        CustomUserDetails currentUser = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UUID userId = currentUser.getUserId();
        TransactionsResponseDTO response = transactionsService.createTransaction(userId, request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{transactionId}")
    public ResponseEntity<SimpleMessage> deleteTransaction(@PathVariable UUID transactionId){
        CustomUserDetails currentUser = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UUID userId = currentUser.getUserId();
        SimpleMessage response = transactionsService.deleteTransaction(userId, transactionId);
        return ResponseEntity.ok(response);
    }

   @GetMapping("/{categoryId}")
   public ResponseEntity<List<TransactionsResponseDTO>> getCategoryTransactions(@PathVariable UUID categoryId){
       CustomUserDetails currentUser = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
       UUID userId = currentUser.getUserId();
       List<TransactionsResponseDTO> response = transactionsService.getCategoryTransactions(userId, categoryId);
       return ResponseEntity.ok(response);
   }

   @PutMapping("/{transactionId}")
   public ResponseEntity<UpdateTransactionResponse> updateTransaction(@PathVariable UUID transactionId, @RequestBody UpdateTransactionDTO update){
       CustomUserDetails currentUser = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
       UUID userId = currentUser.getUserId();
       UpdateTransactionResponse response = transactionsService.updateTransaction(userId, transactionId, update);
       return ResponseEntity.ok(response);
   }

   @GetMapping
    public ResponseEntity<List<TransactionsResponseDTO>> getUserTransactions(){
       CustomUserDetails currentUser = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
       UUID userId = currentUser.getUserId();
       List<TransactionsResponseDTO> response = transactionsService.getUserTransactions(userId);
       return ResponseEntity.ok(response);
   }
}
