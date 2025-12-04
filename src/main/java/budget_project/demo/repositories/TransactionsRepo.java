package budget_project.demo.repositories;

import budget_project.demo.entities.Transactions;
import budget_project.demo.entities.TypeOfTransaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Repository
public interface TransactionsRepo extends JpaRepository<Transactions, UUID> {
    List<Transactions> findByUserId(UUID userId);
    List<Transactions> findByCategoryId(UUID categoryId);




    @Query( nativeQuery = true, value = "SELECT COALESCE(SUM(amount),0) as Total FROM transactions"+
           "WHERE userId=:userId AND type=:type AND createdAt BETWEEN :from AND :to")
    BigDecimal sumAmountByTypeBetweenDates(
           @Param("userId") UUID userId, @Param("type")TypeOfTransaction type,
           @Param("from") LocalDateTime from, @Param("to") LocalDateTime to
   );

   @Query(nativeQuery = true, value = "SELECT categoryId, COALESCE(SUM(amount),0) as Total FROM transactions"+
           "WHERE userId=:userId AND type=:type AND createdAt BETWEEN :from AND :to"+
           "GROUP BY categoryId")
    List<Object[]> sumAmountGroupedByCategory(
            @Param("userId") UUID userId, @Param("type") TypeOfTransaction type,
            @Param("from") LocalDateTime from, @Param("to") LocalDateTime to
   );

   @Query(nativeQuery = true, value= "SELECT DATE(createdAt) as Day, COALESCE(SUM(amount),0) as Total FROM transactions"+
           "WHERE userId=:userId AND type=:type AND createdAt BETWEEN :from AND :to"+
           "GROUP BY Day ORDER BY Day")
    List<Object[]> sumPerDay(
           @Param("userId") UUID userId, @Param("type") TypeOfTransaction type,
           @Param("from") LocalDateTime from, @Param("to")LocalDateTime to
   );

   @Query(nativeQuery = true, value="SELECT to_char(date_trunc('month',createdAt),YYYY-MM) as month, COALESCE(SUM(amount)) as Total"+
           "FROM transactions"+
           "WHERE userId=:userId AND type=:type AND createdAt BETWEEN :from AND :to"+
           "GROUP BY month ORDER BY month")
    List<Object[]> sumPerMonth(
           @Param("userId") UUID userId, @Param("type") TypeOfTransaction type,
           @Param("from") LocalDateTime from, @Param("to")LocalDateTime to
   );




}
