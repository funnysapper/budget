package budget_project.demo.services;

import budget_project.demo.dtos.FullReportDTO;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

public interface ReportingService {
    FullReportDTO generateForRange(UUID userId, LocalDateTime from, LocalDateTime to);
    FullReportDTO daily(UUID userId, LocalDate date);
    FullReportDTO weekly(UUID userId, LocalDate date);
    FullReportDTO monthly(UUID userId, LocalDate date);
    FullReportDTO yearly(UUID userId, LocalDate date);
    FullReportDTO custom(UUID userId, LocalDate start, LocalDate end);

}
