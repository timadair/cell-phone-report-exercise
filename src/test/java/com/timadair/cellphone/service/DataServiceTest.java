package com.timadair.cellphone.service;

import com.timadair.cellphone.data.CellPhone;
import com.timadair.cellphone.data.UsageEntry;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DataServiceTest {


  public static final String CELL_PHONE_TEST_DATA_PATH = "./src/test/resources/testCellData.csv";
  public static final String USAGE_TEST_DATA_PATH = "./src/test/resources/testUsageData.csv";
  private DataService subject;

  @BeforeEach
  public void setup() {
    subject = new DataService();
  }

  @Test
  public void shouldParseCellPhoneEntry() {
    CellPhone expected = new CellPhone(1234567, "Joseph Bishop", "20211012", "zPhone");

    List<CellPhone> cellPhoneRecords = subject.getCellPhoneRecords(CELL_PHONE_TEST_DATA_PATH);
    assertEquals(1, cellPhoneRecords.size());
    assertEquals(expected, cellPhoneRecords.get(0));
  }

  @Test
  public void shouldParseUsageEntries() {
    UsageEntry expectedFirstEntry = new UsageEntry(5, "12/4/2020", 17, 0.01f);
    UsageEntry expectedLastEntry = new UsageEntry(8, "5/24/2020", 90, 0.12f);

    List<UsageEntry> usageRecords = subject.getUsageRecords(USAGE_TEST_DATA_PATH);

    assertEquals(expectedFirstEntry, usageRecords.get(0));
    assertEquals(expectedLastEntry, usageRecords.get(usageRecords.size() - 1));
  }
}
