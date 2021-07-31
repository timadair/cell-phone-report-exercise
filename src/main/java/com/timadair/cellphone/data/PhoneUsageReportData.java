package com.timadair.cellphone.data;

import lombok.Data;
import org.apache.commons.lang3.tuple.Pair;

import java.util.Map;

@Data
public class PhoneUsageReportData {
  final Map<Integer, EmployeeUsageSummary> usageByEmployee;

  public Integer getTotalMinutes() {
    return usageByEmployee.values().stream()
        .flatMap(s -> s.getUsage().values().stream())
        .map(Pair::getLeft)
        .reduce(Integer::sum)
        .orElseThrow(() -> new RuntimeException("Unable to calculate total minutes."));
  }

  public Integer getAverageMinutes() {
    return getTotalMinutes() / getPhoneCount();
  }

  public Float getTotalData() {
    return usageByEmployee.values().stream()
        .flatMap(s -> s.getUsage().values().stream())
        .map(Pair::getRight)
        .reduce((f1, f2) -> Math.round((f1 + f2) * 100) / 100.0f)
        .orElseThrow(() -> new RuntimeException("Unable to calculate total minutes."));
  }

  public Float getAverageData() {
    return Math.round((getTotalData() * 100) / getPhoneCount()) / 100.0f;
  }

  public Integer getPhoneCount() {
    return usageByEmployee.size();
  }

}
