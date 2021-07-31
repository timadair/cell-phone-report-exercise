# cell-phone-report-exercise

## Usage
Build the jar using **mvn clean install**, then run the report with **java -jar [path to jar]**

To run this demo code with other inputs, new files would need to be copied into the main resources directory before installing.

## Assumptions
The majority of assumptions were made about the format of the csv files.  Keeping DataService and DataProcessingService separate is intended to help with adding new ingestion strategies.
- Column order won't change.  The headers in the CSV files are ignored (I did notice the typo in "emplyeeId").  
- Date formats within a column won't change
- Multiple usage entries for the same employee for the same date should be added.
- The start and end dates would be passed in to the program if it were made to be reusable.
- Average means average by employee instead of by month
- The consumer of the report wants both minutes and data in the same table.  Separating them into two tables would help with space constraints.

## Improvements to be made
- The data column for September 2018 is visible when opening the PDF but is being cut off when printing.  
  - Separate the details section into one table per year, place each year on  a separate page.
  - Have Minutes and Data Usage in separate tables
- Have the file locations be passed in as arguments.
- Other general formatting improvements to the PDF to improve readability.  Fonts, color choices, borders, whitespace, etc.
- Add company logo, standard headers and footers, etc.  Rearrange to be printed onto stationary?

## Libraries
**Apache PDFBox**  
My local printer, an HP OfficeJet 5252, is unable to handle PDF format using Java's PrintJob. 
PDFBox worked for my local printer, and is more likely to work for more printers than anything I'd do in an hour.

**Apache Commons CSV**  
For parsing CSV files
 
**Lombok**  
To auto-generate constructors, getters, setters, etc.  Reduces amount of uninteresting code to test.

**iText 7**  
I have experience working with previous versions of iText.  Assuming you want to keep your code closed course, iText should not be used in a production environment without obtaining a commercial license, due to the APGL of the free version.  It is, however, useful for a proof of concept.

**Apache Lang**  
To use Pair in EmployeeUsageSummary