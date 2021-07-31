package com.timadair.cellphone;

import com.timadair.cellphone.data.CellPhone;
import com.timadair.cellphone.data.EmployeeUsageSummary;
import com.timadair.cellphone.data.UsageEntry;
import com.timadair.cellphone.service.DataProcessingService;
import com.timadair.cellphone.service.DataService;
import com.timadair.cellphone.service.PDFService;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.printing.PDFPageable;

import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;
import java.util.Map;

import static java.nio.file.Files.newInputStream;

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

    List<CellPhone> employeePhones = dataService.getCellPhoneRecords(cellPhoneCsvPath);
    List<UsageEntry> usageEntries = dataService.getUsageRecords(cellPhoneUsageCsvPath);
    Map<Integer, EmployeeUsageSummary> phoneUsage = dataProcessingService.processPhoneUsage(employeePhones, usageEntries);
    List<YearMonth> monthsCovered = dataProcessingService.getMonths(reportStartDate, reportEndDate);

    pdfService.renderMonthlyCellPhoneUsageReport(phoneUsage, REPORT_FILE_DEST, reportStartDate, reportEndDate, monthsCovered);

//    printPDFFile(Paths.get(REPORT_FILE_DEST));
//    Files.delete(Paths.get(REPORT_FILE_DEST));
  }

  private static void printPDFFile(Path samplePdfPath) {
    PrintService printService = PrintServiceLookup.lookupDefaultPrintService();
    PrinterJob job = PrinterJob.getPrinterJob();

    try (InputStream fileStream = newInputStream(samplePdfPath, StandardOpenOption.READ)) {
      PDDocument document = PDDocument.load(fileStream);
      job.setPageable(new PDFPageable(document));
      job.setPrintService(printService);
      job.print();
    } catch (PrinterException | IOException ex) {
      ex.printStackTrace();
    }
  }
}