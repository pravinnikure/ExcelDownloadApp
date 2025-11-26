package com.example.ExcelDownload.Utility;

import com.example.ExcelDownload.Entity.Student;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

public class Helper {

    public static String[] HEADER = {
            "Id",
            "FirstName",
            "LastName",
            "Standard"
    };

    public static String FILE_NAME = "Student_Data";

    public static ByteArrayInputStream dataToExcel(List<Student> list) throws IOException {
        Workbook workbook = new XSSFWorkbook();
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        try {

            Sheet sheet = workbook.createSheet(FILE_NAME);

            Row row = sheet.createRow(0);
            for (int i = 0; i < HEADER.length; i++) {
                Cell cell = row.createCell(i);
                cell.setCellValue(HEADER[i]);
            }

            int rowIndex = 1;
            for (Student s : list) {
                Row dataRow = sheet.createRow(rowIndex);
                rowIndex++;

                dataRow.createCell(0).setCellValue(s.getId());
                dataRow.createCell(1).setCellValue(s.getFirstname());
                dataRow.createCell(2).setCellValue(s.getLastname());
                dataRow.createCell(3).setCellValue(s.getStandard());
            }

            workbook.write(out);

            return new ByteArrayInputStream(out.toByteArray());

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } finally {
            workbook.close();
            out.close();
        }


    }


}
