package com.timadair.cellphone.service;

import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.timadair.cellphone.data.EmployeeUsageSummary;

import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

/**
 * Renders PDF reports
 */
public class PDFService {

  public static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("LLL dd',' yyyy");

  public void renderMonthlyCellPhoneUsageReport(Map<Integer, EmployeeUsageSummary> phoneUsageByEmployee, String destinationFilePath, LocalDate reportStartDate, LocalDate reportEndDate, List<YearMonth> monthsCovered) throws FileNotFoundException {
    //Initialize PDF document
    PdfWriter writer = new PdfWriter(destinationFilePath);
    PdfDocument pdf = new PdfDocument(writer);
    pdf.setDefaultPageSize(PageSize.Default.rotate());
    Document document = new Document(pdf);

    //HEADERS
    document.add(new Paragraph("Report Date: " + LocalDateTime.now().format(DATE_FORMAT)));
    // Assume the report should also include the timespan covered the report should cover.
    // These dates could be extracted from the data, but assuming that not every day will have data, it seems
    //   more likely they'd be passed in as parameters for a database query.
    document.add(new Paragraph("Report Date Range: " + reportStartDate.format(DATE_FORMAT) + " - " + reportEndDate.format(DATE_FORMAT)));
    document.add(new Paragraph("# Phones: " + phoneUsageByEmployee.size()));
    document.add(new Paragraph("Total Minutes: " + "Placeholder 3000"));
    document.add(new Paragraph("Average Minutes per employee: " + "Placeholder 500"));
    document.add(new Paragraph("Total Data: " + "Placeholder 60 MB"));
    document.add(new Paragraph("Average Data per employee: " + "Placeholder 10 MB"));

    // DETAILS
    Table detailsTable = new Table(4 + monthsCovered.size() * 2);
    detailsTable.addHeaderCell("Employee ID");
    detailsTable.addHeaderCell("Name");
    detailsTable.addHeaderCell("Model");
    detailsTable.addHeaderCell("Purchase Date");
    for (YearMonth yearMonth : monthsCovered) {
      Cell headerCell = new Cell(1, 2);
      headerCell.add(new Paragraph(yearMonth.format(DateTimeFormatter.ofPattern("LLL yyyy"))));
      detailsTable.addHeaderCell(headerCell);
    }

    document.add(detailsTable);
    document.close();
  }
}
