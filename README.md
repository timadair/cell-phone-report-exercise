# cell-phone-report-exercise

## Usage
Build the jar using **mvn clean install**, then run the report with **java -jar .\target\cell-phone-report-exercise-1.0-SNAPSHOT.jar**

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