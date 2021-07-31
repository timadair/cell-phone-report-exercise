package com.timadair.cellphone;

import com.timadair.cellphone.data.CellPhone;
import com.timadair.cellphone.service.DataService;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.printing.PDFPageable;

import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.List;

import static java.nio.file.Files.newInputStream;

public class CellPhoneUsageReportApp {

  public static final Path SAMPLE_PDF_PATH = Paths.get("./src/test/resources/pdf-sample.pdf");
  public static final String CELL_PHONE_CSV_PATH = "./src/main/resources/CellPhone.csv";

  public static void main(String[] args) {
    DataService dataService = new DataService();
    List<CellPhone> employeePhones = dataService.getCellPhoneRecords(CELL_PHONE_CSV_PATH);
    System.out.println(employeePhones);

//    printPDFFile(SAMPLE_PDF_PATH);
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