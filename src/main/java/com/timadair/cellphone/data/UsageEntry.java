package com.timadair.cellphone.data;

import lombok.Data;

/**
 * Data model for persisted cell phone usage data
 */
@Data
public class UsageEntry {
  private final Integer employeeId;
  private final String date;
  private final Integer totalMinutes;
  private final Float totalData;
}
