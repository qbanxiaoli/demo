package com.util;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.aop.annotation.Excel;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import io.swagger.annotations.ApiModel;
import lombok.Cleanup;
import lombok.SneakyThrows;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.util.FieldUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.InputStreamReader;
import java.io.Reader;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.text.DecimalFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author qbanxiaoli
 * @description
 * @create 2018/10/19 9:32 PM
 */
@Component
public class FileUtil {

    private static final String pattern = "yyyy-MM-dd HH:mm:ss";

    private static HttpServletResponse response;

    public FileUtil(HttpServletResponse response) {
        FileUtil.response = response;
    }

    /**
     * @param multipartFile  导入csv文件流
     * @param componentClass 导入的字节码对象
     */
    @SneakyThrows
    public static <T> List<T> importFromCsv(MultipartFile multipartFile, Class<T> componentClass) {
        // 获取对象的属性列表
        Field[] fields = componentClass.getDeclaredFields();
        // 加载文件流
        @Cleanup Reader reader = new InputStreamReader(multipartFile.getInputStream());
        CSVFormat csvFileFormat = CSVFormat.EXCEL.withFirstRecordAsHeader();
        @Cleanup CSVParser parser = CSVParser.parse(reader, csvFileFormat);
        List<T> list = Lists.newArrayList();
        for (CSVRecord record : parser.getRecords()) {
            T t = componentClass.newInstance();
            int i = 0;
            for (Field field : fields) {
                if (field.getAnnotation(Excel.class) != null && !field.getAnnotation(Excel.class).type().equals(Excel.TypeEnum.EXPORT)) {
                    // 开启私有属性访问权限
                    field.setAccessible(true);
                    if (StringUtils.isNotBlank(record.get(i))) {
                        switch (field.getType().getSimpleName()) {
                            case "String":
                                field.set(t, record.get(i));
                                break;
                            case "Integer":
                                field.set(t, Integer.valueOf(record.get(i)));
                                break;
                            case "Long":
                                field.set(t, Long.valueOf(record.get(i)));
                                break;
                            case "Boolean":
                                field.set(t, Boolean.valueOf(record.get(i)));
                                break;
                            case "BigDecimal":
                                field.set(t, new BigDecimal(record.get(i)));
                                break;
                            case "Date":
                                if (field.getAnnotation(DateTimeFormat.class) != null) {
                                    field.set(t, DateUtils.parseDate(record.get(i), field.getAnnotation(DateTimeFormat.class).pattern()));
                                } else {
                                    field.set(t, DateUtils.parseDate(record.get(i), pattern));
                                }
                        }
                    }
                    i++;
                }
            }
            list.add(t);
        }
        return list;
    }

    /**
     * @param multipartFile  导入excel文件流
     * @param componentClass 导入的字节码对象
     */
    @SneakyThrows
    public static <T> List<T> importFromExcel(MultipartFile multipartFile, Class<T> componentClass) {
        // 加载文件流
        @Cleanup Workbook workbook = WorkbookFactory.create(multipartFile.getInputStream());
        // 读取第一个Sheet
        Sheet sheet = workbook.getSheetAt(0);
        // 获取对象的属性列表
        Field[] fields = componentClass.getDeclaredFields();
        List<T> list = Lists.newArrayList();
        // 遍历行，除去第一行
        for (int i = 1; i < sheet.getLastRowNum() + 1; i++) {
            // 获得行对象
            Row row = sheet.getRow(i);
            if (null != row) {
                T t = componentClass.newInstance();
                int j = 0;
                for (Field field : fields) {
                    if (field.getAnnotation(Excel.class) != null && !field.getAnnotation(Excel.class).type().equals(Excel.TypeEnum.EXPORT)) {
                        // 开启私有属性访问权限
                        field.setAccessible(true);
                        DecimalFormat df = new DecimalFormat("#");
                        if (StringUtils.isNotBlank(row.getCell(j).toString())) {
                            switch (field.getType().getSimpleName()) {
                                case "String":
                                    field.set(t, row.getCell(j).toString());
                                    break;
                                case "Integer":
                                    field.set(t, Integer.valueOf(df.format(new BigDecimal(row.getCell(j).toString()))));
                                    break;
                                case "Long":
                                    field.set(t, Long.valueOf(df.format(new BigDecimal(row.getCell(j).toString()))));
                                    break;
                                case "Boolean":
                                    field.set(t, row.getCell(j).getBooleanCellValue());
                                    break;
                                case "BigDecimal":
                                    field.set(t, row.getCell(j).getNumericCellValue());
                                    break;
                                case "Date":
                                    field.set(t, row.getCell(j).getDateCellValue());
                                    break;
                            }
                        }
                        j++;
                    }
                }
                list.add(t);
            }
        }
        return list;
    }

    /**
     * @param list           导出的对象集合
     * @param componentClass 导出的字节码对象
     */
    public static void exportToCSV(List<?> list, Class<?> componentClass) {
        // 导出文件名称
        String fileName = componentClass.getAnnotation(ApiModel.class).value();
        // 添加单元格标题
        List<String> titles = FileUtil.getTitles(componentClass);
        // 添加导出的属性列，与标题一一对应，顺序要一致
        List<String> fieldsName = FileUtil.getFieldsName(componentClass);
        FileUtil.exportToCSV(list, fileName, titles, fieldsName);
    }

    /**
     * @param list           导出的对象集合
     * @param componentClass 导出的字节码对象
     */
    public static void exportToExcel(List<?> list, Class<?> componentClass) {
        // 导出工作薄名称
        String sheetName = componentClass.getAnnotation(ApiModel.class).value();
        // 添加单元格标题
        List<String> titles = FileUtil.getTitles(componentClass);
        // 添加导出的属性列，与标题一一对应，顺序要一致
        List<String> fieldsName = FileUtil.getFieldsName(componentClass);
        FileUtil.exportToExcel(list, sheetName, titles, fieldsName);
    }

    /**
     * @param list       导出的对象集合
     * @param fileName   导出文件名称
     * @param titles     导出文件的标题
     * @param fieldsName 填充单元格的属性字段
     */
    @SneakyThrows
    private static void exportToCSV(List<?> list, String fileName, List<String> titles, List<String> fieldsName) {
        // list转数组
        String[] headers = new String[titles.size()];
        titles.toArray(headers);
        // 设置表头
        CSVFormat csvFormat = CSVFormat.DEFAULT.withHeader(headers);
        StringBuilder stringBuilder = new StringBuilder();
        @Cleanup CSVPrinter csvPrinter = new CSVPrinter(stringBuilder, csvFormat);
        // 组装数据
        for (Object obj : list) {
            Map<String, Object> map = Maps.newLinkedHashMap();
            for (String str : fieldsName) {
                // 获取fieldsName属性对应的值，并添加到map中
                Object object = FieldUtils.getFieldValue(obj, str);
                // 如果属性字段为时间，进行格式转换
                if (object instanceof Date) {
                    Field field = FieldUtils.getField(obj.getClass(), str);
                    if (field.getAnnotation(JsonFormat.class) != null) {
                        map.put(str, DateFormatUtils.format((Date) object, field.getAnnotation(JsonFormat.class).pattern()));
                    } else {
                        map.put(str, object);
                    }
                } else {
                    map.put(str, object);
                }
            }
            csvPrinter.printRecord(map.values());
        }
        FileUtil.setHttpServletResponse(stringBuilder.toString().getBytes(), fileName.concat(".csv"));
    }

    /**
     * @param list       导出的对象集合
     * @param sheetName  导出的Excel工作薄名称
     * @param titles     导出文件的标题
     * @param fieldsName 填充单元格的属性字段
     */
    @SneakyThrows
    private static void exportToExcel(List<?> list, String sheetName, List<String> titles, List<String> fieldsName) {
        // 创建工作簿对象
        @Cleanup Workbook workbook = new XSSFWorkbook();
        // 创建sheet
        Sheet sheet = workbook.createSheet(sheetName);
        // 创建row,第一行为表头
        Row row = sheet.createRow(0);
        // 创建单元格，添加标题
        for (int i = 0; i < titles.size(); i++) {
            // 创建单元格
            Cell cell = row.createCell(i);
            // 设置标题样式
            cell.setCellStyle(FileUtil.getColumnTopStyle(workbook));
            // 填充值
            cell.setCellValue(titles.get(i));
        }
        // 添加数据,循环创建List长度个row
        for (int i = 0; i < list.size(); i++) {
            // 创建row,行数据
            Row rowData = sheet.createRow(i + 1);
            for (int j = 0; j < fieldsName.size(); j++) {
                // 创建单元格列
                Cell cell = rowData.createCell(j);
                // 设置单元格样式
                cell.setCellStyle(FileUtil.getColumnStyle(workbook));
                // 获取fieldsName属性对应的值，并填充到单元格中
                Object object = FieldUtils.getFieldValue(list.get(i), fieldsName.get(j));
                if (object != null) {
                    // 如果属性字段为时间，进行格式转换
                    if (object instanceof Date) {
                        Field field = FieldUtils.getField(list.get(i).getClass(), fieldsName.get(j));
                        if (field.getAnnotation(JsonFormat.class) != null) {
                            cell.setCellValue(DateFormatUtils.format((Date) object, field.getAnnotation(JsonFormat.class).pattern()));
                        } else {
                            cell.setCellValue(DateFormatUtils.format((Date) object, pattern));
                        }
                    } else {
                        cell.setCellValue(object.toString());
                    }
                }
            }
        }
        FileUtil.setHttpServletResponse(sheetName.concat(".xlsx"));
        workbook.write(response.getOutputStream());
    }

    @SneakyThrows
    private static List<String> getTitles(Class<?> componentClass) {
        List<String> titles = Lists.newArrayList();
        // 获取对象的属性列表
        Field[] fields = componentClass.getDeclaredFields();
        for (Field field : fields) {
            if (field.getAnnotation(Excel.class) != null && !field.getAnnotation(Excel.class).type().equals(Excel.TypeEnum.IMPORT)) {
                titles.add(field.getAnnotation(Excel.class).value());
            }
        }
        return titles;
    }

    private static List<String> getFieldsName(Class<?> componentClass) {
        List<String> fieldsName = Lists.newArrayList();
        // 获取对象的属性列表
        Field[] fields = componentClass.getDeclaredFields();
        for (Field field : fields) {
            if (field.getAnnotation(Excel.class) != null && !field.getAnnotation(Excel.class).type().equals(Excel.TypeEnum.IMPORT)) {
                fieldsName.add(field.getName());
            }
        }
        return fieldsName;
    }

    @SneakyThrows
    public static void setHttpServletResponse(byte[] bytes, String fileName) {
        FileUtil.setHttpServletResponse(fileName);
        response.getOutputStream().write(bytes);
    }

    @SneakyThrows
    private static void setHttpServletResponse(String fileName) {
        response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=" + new String(fileName.getBytes(StandardCharsets.UTF_8), StandardCharsets.ISO_8859_1));
        response.setContentType(MediaType.APPLICATION_OCTET_STREAM_VALUE);
    }

    /**
     * 列头单元格样式
     */
    private static CellStyle getColumnTopStyle(Workbook workbook) {
        // 设置字体
        Font font = workbook.createFont();
        // 设置字体大小
        font.setFontHeightInPoints((short) 10);
        // 字体加粗
        font.setBold(Boolean.TRUE);
        // 设置字体名字
        font.setFontName(HSSFFont.FONT_ARIAL);
        // 设置样式
        CellStyle style = workbook.createCellStyle();
        // 设置填充方案
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        // 设置背景色
        style.setFillForegroundColor(IndexedColors.WHITE.index);
        // 设置低边框
        style.setBorderBottom(BorderStyle.THIN);
        // 设置低边框颜色
        style.setBottomBorderColor(HSSFColor.HSSFColorPredefined.BLACK.getIndex());
        // 设置右边框
        style.setBorderRight(BorderStyle.THIN);
        // 设置顶边框
        style.setTopBorderColor(HSSFColor.HSSFColorPredefined.BLACK.getIndex());
        // 设置顶边框颜色
        style.setTopBorderColor(HSSFColor.HSSFColorPredefined.BLACK.getIndex());
        // 在样式中应用设置的字体
        style.setFont(font);
        // 设置自动换行
        style.setWrapText(Boolean.FALSE);
        // 设置水平对齐的样式为居中对齐
        style.setAlignment(HorizontalAlignment.CENTER);
        // 设置垂直对齐的样式为居中对齐
        style.setVerticalAlignment(VerticalAlignment.CENTER);
        return style;
    }

    /**
     * 单元格样式
     */
    private static CellStyle getColumnStyle(Workbook workbook) {
        // 设置字体
        Font font = workbook.createFont();
        // 设置字体大小
        font.setFontHeightInPoints((short) 10);
        // 字体加粗
        font.setBold(Boolean.FALSE);
        // 设置字体名字
        font.setFontName(XSSFFont.DEFAULT_FONT_NAME);
        // 设置样式
        CellStyle style = workbook.createCellStyle();
        // 设置填充方案
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        // 设置背景色
        style.setFillForegroundColor(IndexedColors.WHITE.index);
        // 设置底边框
        style.setBorderBottom(BorderStyle.THIN);
        // 设置底边框颜色
        style.setBottomBorderColor(HSSFColor.HSSFColorPredefined.BLACK.getIndex());
        // 设置左边框
        style.setBorderLeft(BorderStyle.THIN);
        // 设置左边框颜色
        style.setLeftBorderColor(HSSFColor.HSSFColorPredefined.BLACK.getIndex());
        // 设置右边框
        style.setBorderRight(BorderStyle.THIN);
        // 设置右边框颜色
        style.setRightBorderColor(HSSFColor.HSSFColorPredefined.BLACK.getIndex());
        // 设置顶边框
        style.setBorderTop(BorderStyle.THIN);
        // 设置顶边框颜色
        style.setTopBorderColor(HSSFColor.HSSFColorPredefined.BLACK.getIndex());
        // 在样式用应用设置的字体
        style.setFont(font);
        // 设置自动换行
        style.setWrapText(Boolean.TRUE);
        // 设置水平对齐的样式为居中对齐
        style.setAlignment(HorizontalAlignment.CENTER);
        // 设置垂直对齐的样式为居中对齐
        style.setVerticalAlignment(VerticalAlignment.CENTER);
        return style;
    }

}
