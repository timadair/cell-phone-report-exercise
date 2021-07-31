package com.timadair.cellphone;

import com.timadair.cellphone.data.CellPhone;
import com.timadair.cellphone.data.PhoneUsageReportData;
import com.timadair.cellphone.data.UsageEntry;
import com.timadair.cellphone.service.DataProcessingService;
import com.timadair.cellphone.service.DataService;
import com.timadair.cellphone.service.PDFService;
import com.timadair.cellphone.service.PrintService;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;

public class CellPhoneUsageReportApp {

  public static final String REPORT_FILE_DEST = "./src/main/resources/tmp/UsageReport.pdf";

  public static void main(String[] args) throws IOException {
    // Acting as though these were arguments passed in.
    LocalDate reportStartDate = LocalDate.parse("2017-09-21");
    LocalDate reportEndDate = LocalDate.parse("2018-09-20");
    String cellPhoneCsvPath = "./src/main/resources/CellPhone.csv";
    String cellPhoneUsageCsvPath = "./src/main/resources/CellPhoneUsageByMonth.csv";

    // Potential improvement: replace this with usage output.
    if (reportStartDate.isAfter(reportEndDate)) {
      System.out.println("Report should not end before it starts");
      return;
    } else if (Files.notExists(Paths.get(cellPhoneCsvPath))) {
      System.out.println("Unable to find file " + cellPhoneCsvPath);
      return;
    } else if (Files.notExists(Paths.get(cellPhoneUsageCsvPath))) {
      System.out.println("Unable to find file " + cellPhoneUsageCsvPath);
      return;
    }

    DataService dataService = new DataService();
    PDFService pdfService = new PDFService();
    DataProcessingService dataProcessingService = new DataProcessingService();
    PrintService printService = new PrintService();

    List<CellPhone> employeePhones = dataService.getCellPhoneRecords(cellPhoneCsvPath);
    List<UsageEntry> usageEntries = dataService.getUsageRecords(cellPhoneUsageCsvPath);
    PhoneUsageReportData reportData = dataProcessingService.processPhoneUsage(employeePhones, usageEntries);
    List<YearMonth> monthsCovered = dataProcessingService.getMonths(reportStartDate, reportEndDate);

    pdfService.renderMonthlyCellPhoneUsageReport(reportData, REPORT_FILE_DEST, reportStartDate, reportEndDate, monthsCovered);

    printService.printPDFFile(Paths.get(REPORT_FILE_DEST));
    Files.delete(Paths.get(REPORT_FILE_DEST));
  }
}