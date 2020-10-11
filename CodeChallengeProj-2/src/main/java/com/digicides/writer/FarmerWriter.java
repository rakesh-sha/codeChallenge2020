package com.digicides.writer;

import static java.util.Arrays.asList;

import java.util.Arrays;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.springframework.batch.item.ItemWriter;

import com.digicides.entity.Farmer;

/**
 *  class that adds a new row to a spreadsheet for each Farmer in the repository
 *  
 * @author rakesh
 *
 */
public class FarmerWriter implements ItemWriter<Farmer> {

	private final Sheet sheet;

    public FarmerWriter(Sheet sheet) {
        this.sheet = sheet;
        //createHeaderRow(sheet);
    }

	/*	@Override
		public void write(List<? extends Farmer> items) throws Exception {
			 System.out.println("FarmerWriter.write()");
			    int rowCount = 0;
			    System.out.println(items);
			 
			    for (Farmer farmer : items) {
			        Row row = sheet.createRow(++rowCount);
			        writeBook(farmer, row);
			    }
			 
			
		}
	
		private void writeBook(Farmer farmer, Row row) {
			    Cell cell = row.createCell(1);
			    cell.setCellValue(farmer.getFo_Number());
			 
			    cell = row.createCell(2);
			    cell.setCellValue(farmer.getTerritory());
			    
			    cell = row.createCell(3);
			    cell.setCellValue(farmer.getFo_Number());
			 
			    cell = row.createCell(4);
			    cell.setCellValue(farmer.getCall_Time());
			}
		
	private void createHeaderRow(Sheet sheet) {
		 
	CellStyle cellStyle = sheet.getWorkbook().createCellStyle();
	Font font = sheet.getWorkbook().createFont();
	font.setBold(true);
	font.setFontHeightInPoints((short) 16);
	cellStyle.setFont(font);
	 
	Row row = sheet.createRow(0);
	
	Cell cellFo_Number = row.createCell(0);
	cellFo_Number.setCellStyle(cellStyle);
	cellFo_Number.setCellValue("Fo_Number");
	 
	Cell territory = row.createCell(1);
	territory.setCellStyle(cellStyle);
	territory.setCellValue("Territory");
	 
	Cell farmer_Number = row.createCell(2);
	farmer_Number.setCellStyle(cellStyle);
	farmer_Number.setCellValue("Farmer_Number");
	
	Cell call_Time = row.createCell(3);
	call_Time.setCellStyle(cellStyle);
	call_Time.setCellValue("Call_Time");
	}*/

	@Override
	public void write(List<? extends Farmer> list) {
	    for (int i = 0; i < list.size(); i++) {
	        writeRow(i, list.get(i));
	    }
	}
	
	private void writeRow(int currentRowNumber, Farmer Farmer) {
	    List<String> columns = prepareColumns(Farmer);
	    Row row = this.sheet.createRow(currentRowNumber);
	    for (int i = 0; i < columns.size(); i++) {
	        writeCell(row, i, columns.get(i));
	    }
	}
	
	private List<String> prepareColumns(Farmer farmer) {
	    return Arrays.asList(
	            farmer.getFo_Number().toString(),
	            farmer.getTerritory(),
	            farmer.getFarmer_Number().toString(),
	            farmer.getCall_Time()
	    );
	}
	
	private void writeCell(Row row, int currentColumnNumber, String value) {
	    Cell cell = row.createCell(currentColumnNumber);
	    cell.setCellValue(value);
	}
}
