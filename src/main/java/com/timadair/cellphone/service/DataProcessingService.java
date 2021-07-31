package com.timadair.cellphone.service;

import com.timadair.cellphone.data.CellPhone;
import com.timadair.cellphone.data.EmployeeUsageSummary;
import com.timadair.cellphone.data.UsageEntry;

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
}
