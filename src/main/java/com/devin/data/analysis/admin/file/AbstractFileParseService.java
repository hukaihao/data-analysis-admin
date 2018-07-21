package com.devin.data.analysis.admin.file;

import com.devin.data.analysis.admin.core.util.RegexUtil;
import com.devin.data.analysis.admin.file.annotation.ParseTemplate;
import com.devin.data.analysis.admin.file.exception.LogicValidationException;
import com.devin.data.analysis.admin.file.exception.ParseError;
import com.devin.data.analysis.admin.file.exception.PhysicalValidationException;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.ss.usermodel.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.lang.reflect.Field;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;

@Component
public abstract class AbstractFileParseService {

    private static final Logger logger = LoggerFactory.getLogger(AbstractFileParseService.class);

    public <T> void upload(MultipartFile file, Class<T> cls) throws Exception {
        List<ParseError> errorList = new ArrayList<>();
        try {
            validateBeforeParse(file);
            List<T> dataList = parse(file.getInputStream(), cls, errorList);
            validateAfterParse(dataList);
        } catch (PhysicalValidationException e) {
            logger.info("物理校验出错,错误内容 : ", e);
            errorList = e.getErrorList();
            throw e;
        } catch (LogicValidationException e) {
            errorList = e.getErrorList();
            logger.info("逻辑校验出错,错误内容 : ", e);
            throw e;
        } catch (Exception e) {
            logger.info("文件解析异常，联系IT处理 {}", e);
            logger.error("文件解析异常", e);
            errorList.add(new ParseError("文件解析异常" + e.getMessage()));
            throw e;
        } finally {
            if (!CollectionUtils.isEmpty(errorList)) {
                handleError(errorList, getErrorFilePath(file));
            }
        }
    }

    public <T> void validateBeforeParse(MultipartFile file) throws PhysicalValidationException {
        List<ParseError> errorList = new ArrayList<>();
        String fileName = file.getOriginalFilename();
        logger.debug("fileName is {} ", fileName);
        if (!isExcelFileType(fileName)) {
            errorList.add(new ParseError("不支持的文件类型,只允许Excel文件"));
            throw new PhysicalValidationException(errorList);
        }

    }

    public <T> List<T> parse(InputStream inputStream, Class<T> cls, List<ParseError> errorList) throws Exception {
        List<T> dataList = new LinkedList<T>();
        try (Workbook workbook = WorkbookFactory.create(inputStream)) {
            //获取数据bean的文字描述和字段
            Map<String, Field> clsDesc = getClassFieldDesc(cls);

            Sheet sheet = workbook.getSheetAt(0);//暂时只支持解析首个sheet页
            Row sRow = sheet.getRow(0);
            //获取表头的文字描述
            Map<String, Integer> rowDesc = getStartRowDesc(sRow);

            if (!isTemplateRight(clsDesc, rowDesc)) {
                errorList.add(new ParseError("模板不正确，请检查表头描述和列数"));
                throw new PhysicalValidationException(errorList);
            }

            //获取文件列和bean的字段对应关系
            Map<Integer, Field> fieldMap = getFieldIndex(clsDesc, rowDesc);
            //从第二行解析数据文件
            int totalRow = sheet.getPhysicalNumberOfRows();
            for (int i = sheet.getFirstRowNum() + 1; i < totalRow; i++) {
                Row row = sheet.getRow(i);
                T t = cls.newInstance();
                int totalCell = row.getLastCellNum();
                for (int j = row.getFirstCellNum(); j < totalCell; j++) {
                    Cell cell = row.getCell(j);
                    if (!CellType.STRING.equals(cell.getCellTypeEnum())) {
                        errorList.add(new ParseError(String.format("上传的excel文件中数据格式为数值型，" +
                                "请修改成文本型后重新上传！错误数据在第%s行，第%s列", i + 1, j + 1)));
                        throw new PhysicalValidationException(errorList);
                    }
                    Field field = fieldMap.get(j);
                    String cellValue = getCellValue(cell);
                    Object beanValue = getBeanValue(cellValue, field);
                    if (rowValidate(cellValue, field, errorList, i, j)) {
                        field.set(t, beanValue);
                    }

                }
                dataList.add(t);
            }

        }
        return dataList;
    }

    private Object getBeanValue(String cellValue, Field field) {
        ParseTemplate annotation = field.getAnnotation(ParseTemplate.class);
        String type = field.getGenericType().toString();
        logger.debug("Field type is : {}", type);
        Object obj = null;
        if (type.indexOf("LocalDateTime") > -1) {
            String formatter = annotation.formatter();
            logger.debug("formatter is : {}", formatter);
            if (!StringUtils.isEmpty(formatter)) {
                obj = com.devin.data.analysis.admin.core.date.DateUtil.stringToDate(cellValue, formatter);
            } else {
                obj = com.devin.data.analysis.admin.core.date.DateUtil.stringToDate(cellValue);
            }
        }
        if (type.indexOf("Byte") > -1) {
            obj = Byte.parseByte(cellValue);
        }
        return obj;
    }

    private boolean rowValidate(String cellValue, Field field, List<ParseError> errorList, int row, int cell) throws Exception {
        ParseTemplate annotation = field.getAnnotation(ParseTemplate.class);
        boolean flag = true;
        if (annotation != null) {
            if (!annotation.nullable() && StringUtils.isEmpty(cellValue)) {
                errorList.add(new ParseError(String.format("第%s行，第%s列该字段不可为空", row + 1, cell + 1)));
                flag = false;
            }
            if (!StringUtils.isEmpty(annotation.ruleRegex())) {
                if (!RegexUtil.isMatch(annotation.ruleRegex(), cellValue)) {
                    StringBuilder sb = new StringBuilder(String.format("第%s行，第%s列字段", row + 1, cell + 1));
                    errorList.add(new ParseError(sb.append(annotation.ruleDesc()).toString()));
                    flag = false;
                }
            }
        }
        return flag;
    }

    public <T> void validateAfterParse(List<T> dataList) throws LogicValidationException {
    }

    private String handleError(List<ParseError> errorList, String errorPath) {
        CSVUtil.createErrorFile(errorList, errorPath);
        return null;
    }

    public String getErrorFilePath(MultipartFile file) {
        String filePath = file.getOriginalFilename();
        filePath = filePath.substring(filePath.lastIndexOf("\\") + 1, filePath.length());
        filePath = filePath.replaceAll("\\.xl(sx|s)", "Error.txt");
        logger.debug("Error file path is : {} ", filePath);
        return filePath;
    }

    /**
     * 判断是否是Excel文件格式
     *
     * @param fileName
     * @return
     */
    private boolean isExcelFileType(String fileName) {
        if (!fileName.endsWith(".xls") && !fileName.endsWith(".xlsx")) {
            return false;
        }
        return true;
    }

    /**
     * 获取数据bean的文字描述和字段
     *
     * @param cls
     * @param <T>
     * @return
     */
    private <T> Map<String, Field> getClassFieldDesc(Class<T> cls) {
        Map<String, Field> descMap = new HashMap<>();
        Field[] fields = cls.getDeclaredFields();
        for (Field field : fields) {
            ParseTemplate annotation = field.getAnnotation(ParseTemplate.class);
            if (annotation != null) {
                String value = annotation.description();
                field.setAccessible(true);
                descMap.put(value, field);
            }
        }
        return descMap;
    }

    /**
     * 获取表头的文字描述
     *
     * @param row
     * @return
     */
    private HashMap<String, Integer> getStartRowDesc(Row row) {
        HashMap<String, Integer> descMap = new HashMap<>();
        int cellNum = row.getLastCellNum();
        for (int j = row.getFirstCellNum(); j < cellNum; j++) { //首行提取注解
            String cellValue = (String) getCellValue(row.getCell(j));
            if (cellValue != null) {
                descMap.put(cellValue, Integer.valueOf(j));
            }
        }
        return descMap;
    }

    private boolean isTemplateRight(Map<String, Field> clsDesc, Map<String, Integer> rowDesc) {
        Set<String> clsKey = clsDesc.keySet();
        Set<String> rowKey = rowDesc.keySet();
        return clsKey.equals(rowKey);
    }

    /**
     * 获取文件列和bean的字段对应关系
     *
     * @param clsDesc
     * @param rowDesc
     * @return
     */
    private Map<Integer, Field> getFieldIndex(Map<String, Field> clsDesc, Map<String, Integer> rowDesc) {
        Map<Integer, Field> fieldMap = new HashMap<>();
        clsDesc.forEach((K, V) -> fieldMap.put(rowDesc.get(K), V));
        return fieldMap;
    }

    private String getCellValue(Cell cell) {
        String value = null;
        DecimalFormat df = new DecimalFormat("0");
        logger.debug("cell Type is : {} cell is :{} ", cell.getCellTypeEnum(), cell.toString());
        switch (cell.getCellTypeEnum()) {
            case _NONE:
                break;
            case STRING:
                value = cell.getStringCellValue();
                break;
            case NUMERIC:
                value = df.format(cell.getNumericCellValue());
                break;
            case BOOLEAN:
                value = String.valueOf(cell.getBooleanCellValue());
                break;
            case BLANK:
                break;
            default:
                value = cell.toString();
        }
        logger.debug("Transfer cellValue is : {}", value);
        return value;
    }

    private String parseCell(Cell cell) throws NullPointerException {
        String result = new String();
        switch (cell.getCellTypeEnum()) {
            case NUMERIC:// 数字类型
                if (HSSFDateUtil.isCellDateFormatted(cell)) {// 处理日期格式、时间格式
                    SimpleDateFormat sdf = null;
                    if (cell.getCellStyle().getDataFormat() == HSSFDataFormat.getBuiltinFormat("h:mm")) {
                        sdf = new SimpleDateFormat("HH:mm");
                    } else {// 日期
                        sdf = new SimpleDateFormat("yyyy-MM-dd");
                    }
                    Date date = cell.getDateCellValue();
                    result = sdf.format(date);
                } else if (cell.getCellStyle().getDataFormat() == 58) {
                    // 处理自定义日期格式：m月d日(通过判断单元格的格式id解决，id的值是58)
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    double value = cell.getNumericCellValue();
                    Date date = org.apache.poi.ss.usermodel.DateUtil.getJavaDate(value);
                    result = sdf.format(date);
                } else {
                    double value = cell.getNumericCellValue();
                    CellStyle style = cell.getCellStyle();
                    DecimalFormat format = new DecimalFormat();
                    String temp = style.getDataFormatString();
                    // 单元格设置成常规
                    if (temp.equals("General")) {
                        format.applyPattern("#");
                    }
                    result = format.format(value);
                }
                break;
            case STRING:// String类型
                result = cell.getRichStringCellValue().toString();
                break;
            case BLANK:
                result = "";
            default:
                result = "";
                break;
        }
        return result;
    }

}
