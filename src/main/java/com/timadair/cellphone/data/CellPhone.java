package com.timadair.cellphone.data;

import lombok.Data;

@Data
public class CellPhone {

  private final Integer employeeId;
  private final String employeeName;
  private final String purchaseDate;
  private final String model;
}
