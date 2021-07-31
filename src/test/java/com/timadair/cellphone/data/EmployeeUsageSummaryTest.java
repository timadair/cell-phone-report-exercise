package com.timadair.cellphone.data;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.Month;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

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
}
