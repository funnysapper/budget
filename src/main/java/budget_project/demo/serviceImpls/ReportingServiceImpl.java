package budget_project.demo.serviceImpls;

import budget_project.demo.dtos.CategoryBreakdownDTO;
import budget_project.demo.dtos.DateRange;
import budget_project.demo.dtos.FullReportDTO;
import budget_project.demo.dtos.SummaryDTO;
import budget_project.demo.entities.TypeOfTransaction;
import budget_project.demo.miscellaneous.DateRangeUtil;
import budget_project.demo.repositories.TransactionsRepo;
import budget_project.demo.services.ReportingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ReportingServiceImpl implements ReportingService {

    private final TransactionsRepo transactionsRepository;

    @Override
    @Transactional(readOnly = true)
    public FullReportDTO generateForRange(UUID userId, LocalDateTime from, LocalDateTime to){
        BigDecimal totalIncome = transactionsRepository.sumAmountByTypeBetweenDates(userId, TypeOfTransaction.INCOME, from, to);
        BigDecimal totalExpenses = transactionsRepository.sumAmountByTypeBetweenDates(userId, TypeOfTransaction.EXPENSE, from, to);
        if(totalIncome== null) totalIncome = BigDecimal.ZERO;
        if(totalExpenses==null) totalExpenses = BigDecimal.ZERO;
        SummaryDTO summary = new SummaryDTO(totalIncome,totalExpenses);

        List<Object[]> raw = transactionsRepository.sumAmountGroupedByCategory(userId, TypeOfTransaction.EXPENSE, from, to);
        BigDecimal totalExpenseDivisor = totalExpenses.compareTo(BigDecimal.ZERO)==0 ? BigDecimal.ONE : totalExpenses;

        List<CategoryBreakdownDTO> breakdown = new ArrayList<>();
        for(Object[] r : raw){
            UUID categoryId = r[0] == null ? null : UUID.fromString(r[0].toString());
            BigDecimal total = r[1] == null ? BigDecimal.ZERO : (BigDecimal) r[1];
            CategoryBreakdownDTO dto = new CategoryBreakdownDTO();
            dto.setCategoryId(categoryId);
            dto.setTotal(total);
            double percentageOfTotal = total.divide(totalExpenseDivisor,4, RoundingMode.HALF_UP).multiply(new BigDecimal(100)).doubleValue();
            dto.setPercentageOfTotal(percentageOfTotal);
            breakdown.add(dto);
        }
        FullReportDTO report = new FullReportDTO();
        report.setSummary(summary);
        report.setByCategory(breakdown);
        return report;
    }

    @Override
    public FullReportDTO daily(UUID userId, LocalDate date){
        DateRange dr = DateRangeUtil.Daily(date);
        return generateForRange(userId, dr.getFrom(),dr.getTo());
    }

    @Override
    public FullReportDTO weekly(UUID userId, LocalDate date){
       DateRange dr = DateRangeUtil.Weekly(date);
       return generateForRange(userId, dr.getFrom(),dr.getTo());
    }

    @Override
    public FullReportDTO monthly(UUID userId, LocalDate date){
       DateRange dr = DateRangeUtil.Monthly(date);
       return generateForRange(userId, dr.getFrom(),dr.getTo());
    }

    @Override
    public FullReportDTO yearly(UUID userId, LocalDate date){
       DateRange dr = DateRangeUtil.Yearly(date);
       return generateForRange(userId, dr.getFrom(),dr.getTo());
    }

    @Override
    public FullReportDTO custom(UUID userId, LocalDate start, LocalDate end){
        DateRange dr = DateRangeUtil.customRange(start, end);
        return generateForRange(userId, dr.getFrom(),dr.getTo());
    }

}



