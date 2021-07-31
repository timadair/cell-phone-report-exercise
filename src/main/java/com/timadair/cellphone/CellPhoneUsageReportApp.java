package com.timadair.cellphone;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.printing.PDFPageable;

import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

import static java.nio.file.Files.newInputStream;

public class CellPhoneUsageReportApp {

  public static final Path SAMPLE_PDF_PATH = Paths.get("./src/main/resources/pdf-sample.pdf");

  public static void main(String[] args) {
    printPDFFile(SAMPLE_PDF_PATH);

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