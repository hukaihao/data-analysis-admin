package com.devin.data.analysis.admin.file;

import com.devin.data.analysis.admin.file.exception.ParseError;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.List;

public class CSVUtil {
    private static final Logger logger = LoggerFactory.getLogger(CSVUtil.class);

    public static final String ERROR_FILE_PATH  = "E:\\data\\da\\";
    /**
     * CSV文件生成方法
     *
     * @param head
     * @param dataList
     * @param outPutPath
     * @return
     */
    public static File createCSVFile(List<String> head, List<List<String>> dataList, String outPutPath) {

        File csvFile = null;
        BufferedWriter csvWtriter = null;
        try {
            csvFile = new File(outPutPath);
            File parent = csvFile.getParentFile();
            if (parent != null && !parent.exists()) {
                parent.mkdirs();
            }
            csvFile.createNewFile();

            // GB2312使正确读取分隔符","
            csvWtriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(csvFile), "GBK"));
            // 写入文件头部
            writeRow(head, csvWtriter);

            // 写入文件内容
            for (List<String> row : dataList) {
                writeRow(row, csvWtriter);
            }
            csvWtriter.flush();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                csvWtriter.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return csvFile;
    }

    public static File createErrorFile(List<ParseError> dataList, String outPutPath) {

        File csvFile = null;
        BufferedWriter csvWtriter = null;
        StringBuilder sb = new StringBuilder(ERROR_FILE_PATH).append(outPutPath);
        logger.debug("outputPath is : {} ",sb.toString());
        try {
            csvFile = new File(sb.toString());
            File parent = csvFile.getParentFile();
            if (parent != null && !parent.exists()) {
                parent.mkdirs();
            }
            csvFile.createNewFile();
            // GB2312使正确读取分隔符","
            csvWtriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(csvFile), "GBK"));
            // 写入文件内容
            for (ParseError obj : dataList) {
                logger.debug("ParseError is : {} ",obj.toString());
                writeRow(obj.toString(), csvWtriter);
            }
            csvWtriter.flush();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (csvWtriter != null) {
                    csvWtriter.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return csvFile;
    }

    /**
     * 写一行数据方法
     *
     * @param rowList
     * @param csvWriter
     * @throws IOException
     */
    private static void writeRow(List<String> rowList, BufferedWriter csvWriter) throws IOException {
        // 写入文件头部
        for (String data : rowList) {
            StringBuffer sb = new StringBuffer();
            String rowStr = sb.append("\"").append(data).append("\",").toString();
            csvWriter.write(rowStr);
        }
        csvWriter.newLine();
    }

    /**
     * 写一行数据方法
     *
     * @param rowData
     * @param csvWriter
     * @throws IOException
     */
    private static void writeRow(String rowData, BufferedWriter csvWriter) throws IOException {
        // 写入文件头部
        StringBuffer sb = new StringBuffer();
        String rowStr = sb.append("\"").append(rowData).append("\",").toString();
        csvWriter.write(rowStr);
        csvWriter.newLine();
    }

}
