package budget_project.demo.miscellaneous;

import budget_project.demo.dtos.DateRange;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;

@Data
@AllArgsConstructor
public final class DateRangeUtil {
      public static DateRange Daily(LocalDate date){
          return new DateRange(date.atStartOfDay(),date.atTime(LocalTime.MAX));
      }

      public static DateRange Weekly(LocalDate date){
          LocalDate start = date.with(DayOfWeek.MONDAY);
          LocalDate end = date.plusDays(6);
          return new DateRange(start.atStartOfDay(), end.atTime(LocalTime.MAX));
      }

      public static DateRange Monthly(LocalDate date){
          LocalDate start = date.withDayOfMonth(1);
          LocalDate end = date.withDayOfMonth(date.lengthOfMonth());
          return new DateRange(start.atStartOfDay(), end.atTime(LocalTime.MAX));
      }

      public static DateRange Yearly(LocalDate date){
          LocalDate start = date.withDayOfYear(1);
          LocalDate end = date.withDayOfYear(date.lengthOfYear());
          return new DateRange(start.atStartOfDay(), end.atTime(LocalTime.MAX));
      }

      public static DateRange customRange(LocalDate startDate, LocalDate endDate){
          return new DateRange(startDate.atStartOfDay(), endDate.atTime(LocalTime.MAX));
      }

}
