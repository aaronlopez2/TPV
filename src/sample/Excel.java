package sample;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class Excel {

    public void writeExcel() throws IOException{
        File fExcel = null;
        String name = "";
        System.out.println(java.time.LocalDate.now());
        try {
            name = new com.sun.security.auth.module.NTSystem().getName();
            System.out.println("USUARIO: " +name);
            fExcel = new File("C:\\Users\\"+name+"\\Documents\\excelDoc");
            boolean f = fExcel.mkdir();
            System.out.print("Directory created? "+f);
        } catch(Exception e) {
            e.printStackTrace();
            System.out.println("Error al crear carpeta");
        }
        String nameFile = "Pago "+java.time.LocalDate.now().toString()+".xlsx";
        String path = "C:\\Users\\"+name+"\\Documents\\excelDoc\\"+nameFile;

        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet("Pago "+java.time.LocalDate.now().toString());

        Object[][] bookData = {
                {"Head First Java", "Kathy Serria", 79},
                {"Effective Java", "Joshua Bloch", 36},
                {"Clean Code", "Robert martin", 42},
                {"Thinking in Java", "Bruce Eckel", 35},
        };

        int rowCount = 0;
        for (Object[] aBook : bookData) {
            Row row = sheet.createRow(++rowCount);
            int columnCount = 0;
            for (Object field : aBook) {
                Cell cell = row.createCell(++columnCount);
                if (field instanceof String) {
                    cell.setCellValue((String) field);
                } else if (field instanceof Integer) {
                    cell.setCellValue((Integer) field);
                }
            }
        }


        File excExist = new File(path);
        if(excExist.exists()) {
            System.out.println("ENTRA EN LA CONDICION EXISTE");
            try {
                FileInputStream file = new FileInputStream(new File(path));
                XSSFWorkbook workbook2 = new XSSFWorkbook(file);
                XSSFSheet sheet2 = workbook2.getSheet("Pago "+java.time.LocalDate.now().toString());
                System.out.println("Numero de hoja "+sheet2);
                int lastRow = sheet2.getLastRowNum();
                Object[][] bookData2 = {
                        {"Head First JavaASDADASD", "Kathy SerriaASDAD", 79},
                        {"Effective Java", "Joshua Bloch", 36},
                        {"Clean Code", "Robert martin", 42},
                        {"Thinking in Java", "Bruce Eckel", 35},
                };

                int rowCount2 = lastRow;
                for (Object[] aBook : bookData2) {
                    Row row = sheet.createRow(++rowCount2);
                    int columnCount = 0;
                    for (Object field : aBook) {
                        Cell cell = row.createCell(++columnCount);
                        if (field instanceof String) {
                            cell.setCellValue((String) field);
                        } else if (field instanceof Integer) {
                            cell.setCellValue((Integer) field);
                        }
                    }
                }
                file.close();
                FileOutputStream outFile =new FileOutputStream(new File(path));
                workbook.write(outFile);
                outFile.close();
            } catch (FileNotFoundException fio) {
                fio.printStackTrace();
            }

        } else {
            try (FileOutputStream outputStream = new FileOutputStream("C:\\Users\\"+name+"\\Documents\\excelDoc\\"+nameFile)) {

                workbook.write(outputStream);
                outputStream.close();
            }
        }


    }

    public void recorrerExcel() throws  IOException{

    }


}
