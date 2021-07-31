package com.timadair.cellphone.data;

import lombok.Getter;
import lombok.ToString;
import org.apache.commons.lang3.tuple.Pair;

import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

@ToString
public class EmployeeUsageSummary {
  @Getter private final Integer employeeId;
  @Getter private final String employeeName;
  @Getter private final String phoneModel;
  @Getter private final LocalDate phonePurchaseDate;
  @Getter private final Map<YearMonth, Pair<Integer, Float>> usage;

  public EmployeeUsageSummary(CellPhone cellPhone) {
    employeeId = cellPhone.getEmployeeId();
    employeeName = cellPhone.getEmployeeName();
    phoneModel = cellPhone.getModel();
    phonePurchaseDate = LocalDate.parse(cellPhone.getPurchaseDate(), DateTimeFormatter.ofPattern("yyyyMMdd"));
    usage = new HashMap<>();
  }

  public void addUsageEntry(UsageEntry usageEntry) {

  }
}
