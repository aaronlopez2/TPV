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

    public void writeExcel(Object[] pago) throws IOException{
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

        float precioFinal = (int) pago[4]* (float)pago[5];
        System.out.println("PRECIO FINAL "+precioFinal);
        Object[][] bookData = {
                //{pago}
                {"Id Producto","Marca","Talla","Modelo","Cantidad","Precio"},
                {pago[0].toString(),pago[1].toString(),pago[2].toString(),pago[3].toString(),Integer.parseInt(pago[4].toString()),precioFinal}

        };          //0         1       2           3           4           5
//Object[] data = {idProducto,prodBrand,tallaProd,modeloProd,cantidadProd,precio};


        File excExist = new File(path);
        if(excExist.exists()) {
            System.out.println("ENTRA EN LA CONDICION EXISTE");
            try {
                FileInputStream file = new FileInputStream(new File(path));
                XSSFWorkbook workbook2 = new XSSFWorkbook(file);
                XSSFSheet sheet2 = workbook2.getSheet("Pago "+java.time.LocalDate.now().toString());
                System.out.println("PRIMERA FILA "+sheet2.getFirstRowNum());
                int lastRow = sheet2.getLastRowNum();
                System.out.println("ULTIMA FILA "+lastRow);
                precioFinal = (int)pago[4] * (float)pago[5];
                System.out.println("PRECIO FINAL "+precioFinal);
                Object[][] bookData2 = {
                        //{pago}
                        {pago[0].toString(),pago[1].toString(),pago[2].toString(),pago[3].toString(),Integer.parseInt(pago[4].toString()),precioFinal}
                };

                int rowCount2 = lastRow;
                for (Object[] aBook : bookData2) {
                    Row row = sheet2.createRow(++rowCount2);
                    int columnCount = 0;
                    for (Object field : aBook) {
                        Cell cell = row.createCell(++columnCount);
                        if (field instanceof String) {
                            cell.setCellValue((String) field);
                        } else if (field instanceof Integer) {
                            cell.setCellValue((Integer) field);
                        } else if (field instanceof Float) {
                            cell.setCellValue((Float) field);
                        }
                    }
                }
                file.close();
                FileOutputStream outFile =new FileOutputStream(new File(path));
                workbook2.write(outFile);
                outFile.close();
            } catch (FileNotFoundException fio) {
                fio.printStackTrace();
            }

        } else {
            XSSFWorkbook workbook = new XSSFWorkbook();
            XSSFSheet sheet = workbook.createSheet("Pago "+java.time.LocalDate.now().toString());

            System.out.println("PRECIO :"+Float.parseFloat(pago[5].toString()));
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
                    } else if (field instanceof Float) {
                        cell.setCellValue((Float) field);
                    }
                }
            }
            //file.close();
            FileOutputStream outFile =new FileOutputStream(new File(path));
            workbook.write(outFile);
            outFile.close();


        }






    }



}
