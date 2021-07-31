package com.timadair.cellphone;

import com.timadair.cellphone.data.CellPhone;
import com.timadair.cellphone.data.UsageEntry;
import com.timadair.cellphone.service.DataService;
import com.timadair.cellphone.service.PDFService;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.printing.PDFPageable;

import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.io.*;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.time.LocalDate;
import java.util.List;

import static java.nio.file.Files.newInputStream;

public class CellPhoneUsageReportApp {

  public static final String CELL_PHONE_CSV_PATH = "./src/main/resources/CellPhone.csv";
  public static final String USAGE_CSV_PATH = "./src/main/resources/CellPhoneUsageByMonth.csv";
  public static final String REPORT_FILE_DEST = "./src/main/resources/tmp/UsageReport.pdf";

  public static void main(String[] args) throws IOException {
    LocalDate reportStartDate = LocalDate.parse("2017-09-21");
    LocalDate reportEndDate = LocalDate.parse("2018-09-20");

    DataService dataService = new DataService();
    PDFService pdfService = new PDFService();
    List<CellPhone> employeePhones = dataService.getCellPhoneRecords(CELL_PHONE_CSV_PATH);
    List<UsageEntry> usageEntries = dataService.getUsageRecords(USAGE_CSV_PATH);

    pdfService.renderMonthlyCellPhoneUsageReport(employeePhones, REPORT_FILE_DEST, reportStartDate, reportEndDate);

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