package com.digicides.writer;

import static java.util.Arrays.asList;

import java.util.Arrays;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.streaming.SXSSFSheet;
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
    }

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
