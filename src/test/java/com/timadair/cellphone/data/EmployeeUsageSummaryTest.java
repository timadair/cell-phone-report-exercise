package com.timadair.cellphone.data;

import org.apache.commons.lang3.tuple.Pair;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.Month;
import java.time.YearMonth;

import static org.junit.jupiter.api.Assertions.*;

public class EmployeeUsageSummaryTest {

  @Test
  public void shouldTakeCellPhoneAsConstructor() {
    EmployeeUsageSummary skippyUsage = new EmployeeUsageSummary(new CellPhone(12, "Skippy", "20201012", "zPhone"));
    assertEquals(12, skippyUsage.getEmployeeId());
    assertEquals("Skippy", skippyUsage.getEmployeeName());
    assertEquals(LocalDate.of(2020, Month.OCTOBER, 12), skippyUsage.getPhonePurchaseDate());
    assertEquals("zPhone", skippyUsage.getPhoneModel());
    assertTrue(skippyUsage.getUsage().isEmpty());
  }

  @Test
  public void shouldProcessASingleUsageEntry() {
    Pair<Integer, Float> expectedMonthUsage = Pair.of(15, 0.35f);

    EmployeeUsageSummary skippyUsage = new EmployeeUsageSummary(new CellPhone(12, "Skippy", "20201012", "zPhone"));
    skippyUsage.addUsageEntry(new UsageEntry(12, "2/6/2020", 15, 0.35f));

    assertEquals(expectedMonthUsage, skippyUsage.getUsage().get(YearMonth.of(2020, Month.FEBRUARY)));
  }

  @Test
  public void shouldSumMultipleUsageEntriesForTheSameMonth() {
    Pair<Integer, Float> expectedMonthUsage = Pair.of(39, 0.46f);

    EmployeeUsageSummary skippyUsage = new EmployeeUsageSummary(new CellPhone(12, "Skippy", "20201012", "zPhone"));
    skippyUsage.addUsageEntry(new UsageEntry(12, "2/6/2020", 15, 0.35f));
    skippyUsage.addUsageEntry(new UsageEntry(12, "2/18/2020", 24, 0.11f));

    assertEquals(expectedMonthUsage, skippyUsage.getUsage().get(YearMonth.of(2020, Month.FEBRUARY)));
  }

  @Test
  public void shouldThrowExceptionOnEntryForWrongEmployee() {
    EmployeeUsageSummary skippyUsage = new EmployeeUsageSummary(new CellPhone(12, "Skippy", "20201012", "zPhone"));

    IllegalStateException illegalStateException = assertThrows(IllegalStateException.class, () ->
        skippyUsage.addUsageEntry(new UsageEntry(15, "1/6/2020", 22, 0.5f)));

    assertTrue(illegalStateException.getMessage().contains("Incorrect employee"));
  }
}
