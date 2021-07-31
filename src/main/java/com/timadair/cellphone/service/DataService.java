package com.timadair.cellphone.service;

import com.timadair.cellphone.data.CellPhone;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class DataService {

  public List<CellPhone> getCellPhoneRecords(String cellPhoneCsvPath) {
    try (Reader in = new FileReader(cellPhoneCsvPath)) {
      Iterable<CSVRecord> records = CSVFormat.DEFAULT.parse(in);

      return StreamSupport.stream(records.spliterator(), false)
          .skip(1)
          .map(this::mapToCellPhone)
          .collect(Collectors.toList());

    } catch (IOException e) {
      e.printStackTrace();
      throw new RuntimeException("Unable to retrieve cellphone records");
    }
  }

  CellPhone mapToCellPhone(CSVRecord r) {
    return new CellPhone(Integer.parseInt(r.get(0)), r.get(1), r.get(2), r.get(3));
  }
}
