package com.timadair.cellphone.service;

import com.timadair.cellphone.data.CellPhone;
import com.timadair.cellphone.data.UsageEntry;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class DataService {

  public List<CellPhone> getCellPhoneRecords(String cellPhoneCsvPath) {
    return readData(cellPhoneCsvPath, this::mapToCellPhone, "Unable to retrieve cellphone records");
  }

  public List<UsageEntry> getUsageRecords(String dataFilePath) {
    return readData(dataFilePath, this::mapToUsageEntry, "Unable to retrieve usage records");
  }

  private <R> List<R> readData(String cellPhoneCsvPath, Function<CSVRecord, R> mapToCellPhone, String errorString) {
    try (Reader in = new FileReader(cellPhoneCsvPath)) {
      Iterable<CSVRecord> records = CSVFormat.DEFAULT.parse(in);

      return StreamSupport.stream(records.spliterator(), false)
          .skip(1)
          .map(mapToCellPhone)
          .collect(Collectors.toList());

    } catch (IOException e) {
      e.printStackTrace();
      throw new RuntimeException(errorString);
    }
  }

  CellPhone mapToCellPhone(CSVRecord r) {
    return new CellPhone(Integer.parseInt(r.get(0)), r.get(1), r.get(2), r.get(3));
  }

  private UsageEntry mapToUsageEntry(CSVRecord r) {
    return new UsageEntry(Integer.parseInt(r.get(0)), r.get(1), Integer.parseInt(r.get(2)), Float.parseFloat(r.get(3)));
  }
}
