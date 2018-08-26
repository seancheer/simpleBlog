package com.seancheer.utils;

import com.seancheer.exception.BlogBaseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import java.io.BufferedInputStream;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * 文件相关的操作
 * @author: seancheer
 * @date: 2018/8/26
 **/
public class FileUtils {

    private static final Logger logger = LoggerFactory.getLogger(FileUtils.class);

    /**
     * 把指定文件压缩为zip文件，
     * @param zipFilePath 最终生成的zip的文件完成路径名
     * @param files 所有的文件集合
     */
    public static void zipFiles(String zipFilePath, File[] files) throws BlogBaseException {
        if (StringUtils.isEmpty(zipFilePath) || null == files)
        {
            throw new IllegalArgumentException();
        }

        if (files.length == 0)
        {
            logger.info("Files is empty! No need to zip it!");
            return;
        }

        ZipOutputStream zipOutputStream = null;
        try {
            zipOutputStream = new ZipOutputStream(new FileOutputStream(zipFilePath));

            for (File file: files) {
                ZipEntry entry = new ZipEntry(file.getName());
                zipOutputStream.putNextEntry(entry);
                copyFile(zipOutputStream, file);
                zipOutputStream.closeEntry();
            }
        } catch (IOException e) {
            throw new BlogBaseException(e);
        }
        finally {
            closeStream(zipOutputStream);
        }

    }

    /**
     * 将file对象copy到outpustream
     * @param out
     * @param file
     */
    private static void copyFile(OutputStream out, File file)
    {
        BufferedInputStream inputStream = null;
        try {
            inputStream = new BufferedInputStream(new FileInputStream(file));
            IOUtils.copyStream(out,inputStream);
        } catch (FileNotFoundException e) {
            logger.warn("File not found or not permitted! fileName:" + file.getName());
        } catch (IOException e) {
            logger.warn("Reading file failed! fileName:" + file.getName());
        }
        finally {
           closeStream(inputStream);
        }
    }

    /**
     * 关闭流
     * @param stream
     */
    private static void closeStream(Closeable stream)
    {
        if (null == stream)
        {
            return;
        }

        try {
            if (stream instanceof OutputStream)
            {
                ((OutputStream)stream).flush();
            }

            stream.close();
        } catch (IOException e) {
            logger.error("Closing stream failed!", e);
        }
    }
}
