package com.bosch.automation.testbase;

import com.bosch.automation.utilities.ApplicationProperties;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.testng.Assert;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/** Utility class to read test data from excel sheet **/
public class TestDataReader {
    public static ApplicationProperties properties = new ApplicationProperties();
    public static final String sheetname = "sheetname";
    public Map<String,String> getTestData(String tcId, String sheetName,String testDataPath,String testDataSheetName) throws IOException {

             Map<String, String> rowMap = new HashMap<>();
             String[] columnNames = null;
             boolean result=false;
             int count = 0;
             int columnNameCounter=0; // counter to collect all column names
//             int cellCount=0;  // this counter is used to read data for the test case

        FileInputStream input = new FileInputStream(testDataPath+testDataSheetName);
            XSSFWorkbook wb = new XSSFWorkbook(input);
            Sheet excelSheet = wb.getSheet(sheetName);

        for (Row row : excelSheet) {
                if (count == 0) {
                    int columnCount = row.getLastCellNum() - row.getFirstCellNum();
                    columnNames = new String[columnCount];
                    for (Cell cell : row) {
                        columnNames[columnNameCounter] = cell.toString();
                        columnNameCounter++;
                    }
                    count++;
                } else {
                    for (Cell cell : row) {
                        if(tcId.equals(row.getCell(0).getStringCellValue()))
                        {
                            if(cell.getCellType()!= CellType.BLANK){
                                if(cell.getCellType()==CellType.STRING)
                                rowMap.put(columnNames[cell.getColumnIndex()], cell.toString());
                                else if(cell.getCellType()==CellType.NUMERIC)
                                    rowMap.put(columnNames[cell.getColumnIndex()], Integer.toString((int) cell.getNumericCellValue()));
                                else
                                    Assert.fail("Data provided in excel is not in correct format");
                            }
                            result = true;
                        }
                    }
                    count++;
                }
            if(result)
            {
                break;
            }
        }
        Assert.assertTrue(result,"Test case ID :"+ tcId + " not found in excel sheet ");
        return rowMap;
    }

    public Map<String, String> readTestData(String tcName,String testDataPath,String testDataSheetName) {
        Map<String, String> testData;
        try {
            testData = getTestData(tcName,properties.readProperty(sheetname),testDataPath,testDataSheetName);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return  testData;
    }
}