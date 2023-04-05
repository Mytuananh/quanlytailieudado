package com.dado.quanlytailieu.util;

import org.apache.poi.ss.usermodel.*;

public class ExcelUtil {

    public static CellStyle getHeaderTableStyle(Workbook workbook) {
        CellStyle headerStyle = getCenterBorderThinCellStyle(workbook);
        headerStyle.setFillForegroundColor(IndexedColors.LIGHT_CORNFLOWER_BLUE.getIndex());
        headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        headerStyle.setWrapText(true);
        return headerStyle;
    }

    public static CellStyle getBodyTableStyle(Workbook workbook) {
        return getCenterBorderThinCellStyle(workbook);
    }

    public static CellStyle getCenterBorderThinCellStyle(Workbook workbook) {
        CellStyle dataCellStyle = workbook.createCellStyle();
        dataCellStyle.setAlignment(HorizontalAlignment.CENTER);
        dataCellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        dataCellStyle.setBorderTop(BorderStyle.THIN);
        dataCellStyle.setBorderRight(BorderStyle.THIN);
        dataCellStyle.setBorderBottom(BorderStyle.THIN);
        dataCellStyle.setBorderLeft(BorderStyle.THIN);
        dataCellStyle.setWrapText(true);
        return dataCellStyle;
    }

    public static CellStyle getMergedCellStyle(Workbook workbook) {
        CellStyle mergedCellStyle = workbook.createCellStyle();
        mergedCellStyle.setAlignment(HorizontalAlignment.CENTER);
        mergedCellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
//        mergedCellStyle.setBorderTop(BorderStyle.MEDIUM);
//        mergedCellStyle.setBorderRight(BorderStyle.MEDIUM);
//        mergedCellStyle.setBorderBottom(BorderStyle.MEDIUM);
//        mergedCellStyle.setBorderLeft(BorderStyle.MEDIUM);
        return mergedCellStyle;
    }

}
