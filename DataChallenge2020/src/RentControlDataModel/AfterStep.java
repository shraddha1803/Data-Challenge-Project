package RentControlDataModel;

import sim.engine.SimState;
import sim.engine.Steppable;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileInputStream;
import java.io.IOException;

import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

public class AfterStep implements Steppable {
	
	public void print(SimState state) throws IOException {
		Environment environment = (Environment)state;
		
		// Open Workbook
		FileInputStream inputStream = new FileInputStream(new File("C:\\Users\\Notlo\\OneDrive\\Documents\\Data Challenge 2020\\WithNoRentControl.xlsx"));
        Workbook workbook = WorkbookFactory.create(inputStream);

        Sheet sheet = workbook.getSheetAt(0);
		
        int rowCount = sheet.getLastRowNum();
	    
        Row row = sheet.createRow(++rowCount);
	    int cellid = 0;
	      
	    Cell cell = row.createCell(cellid++);
	    cell.setCellValue(environment.moves); // write total number of moves made
	    
	    cell = row.createCell(cellid++);
	    cell.setCellValue(environment.numIncomeLevel1); // write total number of tenants from each income level
	    cell = row.createCell(cellid++);
	    cell.setCellValue(environment.numIncomeLevel2);
	    cell = row.createCell(cellid++);
	    cell.setCellValue(environment.numIncomeLevel3);
	    cell = row.createCell(cellid++);
	    cell.setCellValue(environment.numIncomeLevel4);
	    cell = row.createCell(cellid++);
	    cell.setCellValue(environment.numIncomeLevel5);
	    cell = row.createCell(cellid++);
	    cell.setCellValue(environment.numIncomeLevel6);
	    cell = row.createCell(cellid++);
	    cell.setCellValue(environment.numIncomeLevel7);
	    cell = row.createCell(cellid++);
	    cell.setCellValue(environment.numIncomeLevel8);
	    cell = row.createCell(cellid++);
	    cell.setCellValue(environment.numIncomeLevel9);
	    cell = row.createCell(cellid++);
	    cell.setCellValue(environment.numIncomeLevel10);
	    
	    inputStream.close();
	    
	    //Write the workbook in file system and close
	    FileOutputStream out = new FileOutputStream(new File("C:\\Users\\Notlo\\OneDrive\\Documents\\Data Challenge 2020\\WithNoRentControl.xlsx"));
	    workbook.write(out);
	    workbook.close();
	    out.close();
	}
	
	public void step(SimState state) {
		try {
			print(state);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
