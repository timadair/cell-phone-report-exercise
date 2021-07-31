package com.timadair.cellphone.service;

import com.timadair.cellphone.data.CellPhone;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DataServiceTest {


  public static final String CELL_PHONE_TEST_DATA_PATH = "./src/test/resources/testCellData.csv";
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
}
