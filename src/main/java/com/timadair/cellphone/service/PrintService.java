package com.timadair.cellphone.service;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.printing.PDFPageable;

import javax.print.PrintServiceLookup;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

import static java.nio.file.Files.newInputStream;

/**
 * Prints a PDF file
 */
public class PrintService {
  public void printPDFFile(Path samplePdfPath) {
    javax.print.PrintService printService = PrintServiceLookup.lookupDefaultPrintService();
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
