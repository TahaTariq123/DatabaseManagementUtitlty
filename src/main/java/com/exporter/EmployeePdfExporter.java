package com.exporter;

import java.awt.Color;
import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Service;

import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import com.model.Employee;
@Service
public class EmployeePdfExporter {
	private List<Employee> listEmp;

	public EmployeePdfExporter(List<Employee> listEmp) {
		super();
		this.listEmp = listEmp;
	}
	
	private void writeTableHeader(PdfPTable table) {
		PdfPCell cell=new PdfPCell();
		cell.setBackgroundColor(Color.WHITE);
		cell.setPadding(5);
        
        Font font = FontFactory.getFont(FontFactory.HELVETICA);
        font.setColor(Color.BLACK);
        
        cell.setPhrase(new Phrase("ID", font));
        
        table.addCell(cell);
         
        cell.setPhrase(new Phrase("Name", font));
        table.addCell(cell);
         
        cell.setPhrase(new Phrase("Salary", font));
        table.addCell(cell);
         
        cell.setPhrase(new Phrase("Ssn", font));
        table.addCell(cell);
	}
	
	private void writeTableData(PdfPTable table) {
		for(Employee employee: listEmp) {
			table.addCell(String.valueOf(employee.getId()));
			table.addCell(employee.getName());
			table.addCell(String.valueOf(employee.getSalary()));
			table.addCell(employee.getSsn());
		}
	}
	
	public void export(HttpServletResponse response)throws DocumentException, IOException{
		Document document=new Document(PageSize.A4);
		PdfWriter.getInstance(document,response.getOutputStream());
		document.open();
		Font font = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
        font.setSize(18);
        font.setColor(Color.BLUE);
        
        Paragraph p = new Paragraph("List Of Employees",font);
        p.setAlignment(Paragraph.ALIGN_CENTER);
        
        document.add(p);
        
        PdfPTable table=new PdfPTable(4);
        table.setWidthPercentage(100f);
        table.setWidths(new float[] {1.5f, 3.5f, 3.0f, 3.0f});
        table.setSpacingBefore(10);
        
        writeTableHeader(table);
        writeTableData(table);
        document.add(table);
        document.close();
	}
}
