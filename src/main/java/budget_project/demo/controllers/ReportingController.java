package budget_project.demo.controllers;

import budget_project.demo.dtos.FullReportDTO;
import budget_project.demo.exceptions.UnauthorizedException;
import budget_project.demo.miscellaneous.CustomUserDetails;
import budget_project.demo.serviceImpls.ReportingServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.UUID;

@RestController
@RequestMapping("/api/reports")
@RequiredArgsConstructor
@Slf4j
public class ReportingController {

    private final ReportingServiceImpl reportingService;

    private UUID currentUserIdFromContext() {
        var auth = org.springframework.security.core.context.SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || !auth.isAuthenticated()) {
            throw new UnauthorizedException("Not authenticated");
        }

        Object principal = auth.getPrincipal();
        if (principal instanceof CustomUserDetails) {
            CustomUserDetails current = (CustomUserDetails) principal;
            return current.getUserId();
        }
        throw new UnauthorizedException("Unexpected principal type");
    }


    @GetMapping("/daily")
    public ResponseEntity<FullReportDTO> daily(
            @RequestParam("date")
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date
    ) {
        UUID userId = currentUserIdFromContext();
        log.debug("Generating daily report for user={} date={}", userId, date);
        FullReportDTO report = reportingService.daily(userId, date);
        return ResponseEntity.ok(report);
    }

    @GetMapping("/weekly")
    public ResponseEntity<FullReportDTO> weekly(
            @RequestParam("date")
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date
    ) {
        UUID userId = currentUserIdFromContext();
        log.debug("Generating weekly report for user={} date={}", userId, date);
        FullReportDTO report = reportingService.weekly(userId, date);
        return ResponseEntity.ok(report);
    }

    @GetMapping("/monthly")
    public ResponseEntity<FullReportDTO> monthly(
            @RequestParam("date")
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date
    ) {
        UUID userId = currentUserIdFromContext();
        log.debug("Generating monthly report for user={} date={}", userId, date);
        FullReportDTO report = reportingService.monthly(userId, date);
        return ResponseEntity.ok(report);
    }

    @GetMapping("/yearly")
    public ResponseEntity<FullReportDTO> yearly(
            @RequestParam("date")
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date
    ) {
        UUID userId = currentUserIdFromContext();
        log.debug("Generating yearly report for user={} date={}", userId, date);
        FullReportDTO report = reportingService.yearly(userId, date);
        return ResponseEntity.ok(report);
    }

    @GetMapping("/custom")
    public ResponseEntity<FullReportDTO> custom(
            @RequestParam("start")
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate start,
            @RequestParam("end")
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate end
    ) {
        UUID userId = currentUserIdFromContext();
        log.debug("Generating custom report for user={} start={} end={}", userId, start, end);
        FullReportDTO report = reportingService.custom(userId, start, end);
        return ResponseEntity.ok(report);
    }
}

