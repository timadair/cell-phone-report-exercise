package com.timadair.cellphone.service;

import com.timadair.cellphone.data.CellPhone;
import com.timadair.cellphone.data.EmployeeUsageSummary;
import com.timadair.cellphone.data.UsageEntry;

import java.time.LocalDate;
import java.time.YearMonth;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Converts data from persistence model to business model
 */
public class DataProcessingService {

  public Map<Integer, EmployeeUsageSummary> processPhoneUsage(List<CellPhone> phones, List<UsageEntry> usage) {
    HashMap<Integer, EmployeeUsageSummary> phoneUsageByEmployeeId = new HashMap<>();
    for (CellPhone phone : phones) {
      phoneUsageByEmployeeId.put(phone.getEmployeeId(), new EmployeeUsageSummary(phone));
    }

    for (UsageEntry usageEntry : usage) {
      phoneUsageByEmployeeId.get(usageEntry.getEmployeeId()).addUsageEntry(usageEntry);
    }
    return phoneUsageByEmployeeId;
  }

  public List<YearMonth> getMonths(LocalDate reportStartDate, LocalDate reportEndDate) {
    List<YearMonth> coveredMonths = new ArrayList<>();

    YearMonth currentMonth = YearMonth.from(reportStartDate);
    YearMonth endMonth = YearMonth.from(reportEndDate);
    while (currentMonth.isBefore(endMonth)) {
      coveredMonths.add(currentMonth);
      currentMonth = currentMonth.plus(1, ChronoUnit.MONTHS);
    }
    if (!coveredMonths.contains(endMonth)) {
      coveredMonths.add(endMonth);
    }
    return coveredMonths;
  }
}
