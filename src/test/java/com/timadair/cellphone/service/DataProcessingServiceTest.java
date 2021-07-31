package com.timadair.cellphone.service;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.Month;
import java.time.YearMonth;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DataProcessingServiceTest {

  @Test
  public void shouldCover12MonthsIfCoveringYearWithCompleteMonths() {
    List<YearMonth> months = new DataProcessingService().getMonths(LocalDate.of(2021, Month.JUNE, 1), LocalDate.of(2022, Month.MAY, 31));

    assertEquals(12, months.size());
    assertEquals(YearMonth.of(2021, Month.JUNE), months.get(0));
    assertEquals(YearMonth.of(2022, Month.MAY), months.get(months.size() - 1));
  }

  @Test
  public void shouldCover13MonthsIfCoveringYearWithPartialMonths() {
    LocalDate startDate = LocalDate.of(2021, Month.JUNE, 13);
    LocalDate endDate = LocalDate.of(2022, Month.JUNE, 12);
    List<YearMonth> months = new DataProcessingService().getMonths(startDate, endDate);

    assertEquals(13, months.size());
    assertEquals(YearMonth.of(2021, Month.JUNE), months.get(0));
    assertEquals(YearMonth.of(2022, Month.JUNE), months.get(months.size() - 1));
  }
}
