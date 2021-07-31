package com.timadair.cellphone.service;

import com.itextpdf.kernel.colors.ColorConstants;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.property.TextAlignment;
import com.timadair.cellphone.data.EmployeeUsageSummary;
import com.timadair.cellphone.data.PhoneUsageReportData;
import org.apache.commons.lang3.tuple.Pair;

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

  public void renderMonthlyCellPhoneUsageReport(PhoneUsageReportData phoneUsageByEmployee, String destinationFilePath, LocalDate reportStartDate, LocalDate reportEndDate, List<YearMonth> monthsCovered) throws FileNotFoundException {
    //Initialize PDF document
    PdfWriter writer = new PdfWriter(destinationFilePath);
    PdfDocument pdf = new PdfDocument(writer);
    pdf.setDefaultPageSize(PageSize.Default.rotate());
    Document document = new Document(pdf);

    //HEADERS
    addReportHeaders(phoneUsageByEmployee, reportStartDate, reportEndDate, document);

    // DETAILS
    Table detailsTable = new Table(4 + monthsCovered.size() * 2);
    detailsTable.setFontSize(8.0f);
    addTableHeaders(monthsCovered, detailsTable);
    addTableDetails(detailsTable, phoneUsageByEmployee, monthsCovered);

    document.add(detailsTable);
    document.close();
  }

  private void addReportHeaders(PhoneUsageReportData phoneUsageByEmployee, LocalDate reportStartDate, LocalDate reportEndDate, Document document) {
    document.add(new Paragraph("Employee Phone Usage Report for " + reportStartDate.format(DATE_FORMAT) + " - " + reportEndDate.format(DATE_FORMAT)).setFontSize(18));
    document.add(new Paragraph("Printed " + LocalDateTime.now().format(DATE_FORMAT)).setFontSize(9));
    // Assume the report should also include the timespan covered the report should cover.
    // These dates could be extracted from the data, but assuming that not every day will have data, it seems
    //   more likely they'd be passed in as parameters for a database query.
    document.add(new Paragraph(phoneUsageByEmployee.getPhoneCount() + " phones"));
    document.add(new Paragraph(phoneUsageByEmployee.getTotalMinutes() + " minutes total"));
    document.add(new Paragraph(phoneUsageByEmployee.getAverageMinutes() + " minutes average per employee"));
    document.add(new Paragraph(phoneUsageByEmployee.getTotalData() + " MB data used"));
    document.add(new Paragraph(phoneUsageByEmployee.getAverageData() + " MB used per employee"));
  }

  private void addTableHeaders(List<YearMonth> monthsCovered, Table detailsTable) {

    detailsTable.addHeaderCell(headerCell("Employee ID"));
    detailsTable.addHeaderCell(headerCell("Name"));
    detailsTable.addHeaderCell(headerCell("Model"));
    detailsTable.addHeaderCell(headerCell("Purchase Date"));
    for (YearMonth yearMonth : monthsCovered) {
      Cell headerCell = new Cell(1, 2);
      headerCell.setBackgroundColor(ColorConstants.LIGHT_GRAY);
      headerCell.setTextAlignment(TextAlignment.CENTER);
      headerCell.add(new Paragraph(yearMonth.format(DateTimeFormatter.ofPattern("LLL yyyy"))));
      detailsTable.addHeaderCell(headerCell);
    }
  }

  private Cell headerCell(String text) {
    Cell cell = new Cell();
    cell.setBackgroundColor(ColorConstants.LIGHT_GRAY);
    cell.add(new Paragraph(text));
    cell.setTextAlignment(TextAlignment.CENTER);
    return cell;
  }

  private void addTableDetails(Table detailsTable, PhoneUsageReportData phoneUsageByEmployee, List<YearMonth> monthsCovered) {
    phoneUsageByEmployee.getUsageByEmployee().entrySet().stream()
        .sorted(Map.Entry.comparingByKey())
        .forEachOrdered(e -> {
          EmployeeUsageSummary usageSummary = e.getValue();
          detailsTable.addCell(usageSummary.getEmployeeId().toString());
          detailsTable.addCell(usageSummary.getEmployeeName());
          detailsTable.addCell(usageSummary.getPhoneModel());
          detailsTable.addCell(usageSummary.getPhonePurchaseDate().format(DATE_FORMAT));
          Map<YearMonth, Pair<Integer, Float>> usage = usageSummary.getUsage();
          for (YearMonth yearMonth : monthsCovered) {
            if (usage.containsKey(yearMonth)) {
              detailsTable.addCell(usage.get(yearMonth).getLeft().toString());
              detailsTable.addCell(usage.get(yearMonth).getRight().toString() + " MB");
            } else {
              detailsTable.addCell("-");
              detailsTable.addCell("-");
            }
          }
        });
  }
}
