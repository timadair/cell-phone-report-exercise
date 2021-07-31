package com.timadair.cellphone.service;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.timadair.cellphone.data.CellPhone;

import java.io.FileNotFoundException;
import java.util.List;

public class PDFService {
  public void render(List<CellPhone> employeePhones, String s) throws FileNotFoundException {
    PdfWriter writer = new PdfWriter(s);

    //Initialize PDF document
    PdfDocument pdf = new PdfDocument(writer);

    // Initialize document
    Document document = new Document(pdf);

    //Add paragraph to the document
    document.add(new Paragraph(employeePhones.toString()));

    //Close document
    document.close();
  }
}
