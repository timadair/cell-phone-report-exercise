package com.timadair.cellphone.data;

import lombok.Getter;
import lombok.ToString;
import org.apache.commons.lang3.tuple.Pair;

import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BiFunction;

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
    if (!this.employeeId.equals(usageEntry.getEmployeeId())) {
      throw new IllegalStateException("Incorrect employeeId: " + usageEntry.getEmployeeId() + ", expected " + this.employeeId);
    }
    YearMonth yearMonth = YearMonth.parse(usageEntry.getDate(), DateTimeFormatter.ofPattern("M'/'d'/'yyyy"));
    usage.computeIfAbsent(yearMonth, ym -> Pair.of(0, 0.0f));
    usage.compute(yearMonth, sum(usageEntry));
  }

  private BiFunction<YearMonth, Pair<Integer, Float>, Pair<Integer, Float>> sum(UsageEntry usageEntry) {
    return (ym, totalUsage) ->
        Pair.of(
            totalUsage.getLeft() + usageEntry.getTotalMinutes(),
            Math.round((totalUsage.getRight() + usageEntry.getTotalData()) * 100) / 100.0f
        );
  }
}
