package com.seancheer.backupservice;


import com.seancheer.exception.BlogBaseException;
import junit.framework.TestCase;
import net.spy.memcached.MemcachedClient;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetSocketAddress;

/**
 * 测试邮箱备份功能
 *
 * @author: seancheer
 * @date: 2018/8/19
 **/
public class TestSimpleBlogBackupImpl extends TestCase {

    private static final Logger logger = LoggerFactory.getLogger(TestSimpleBlogBackupImpl.class);

    public SimpleBlogBackupImpl blogBackup = new SimpleBlogBackupImpl();

    @Override
    protected void setUp() throws Exception {
        super.setUp();
    }

    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }


    /**
     * 测试发送邮件的功能
     */
    @Test
    public void testSendingEmail() {
        String archievPath = blogBackup.getArchivePath();
        String filePath = archievPath + "/temp.txt";
        File textFile = new File(filePath);
        if (textFile.exists()) {
            textFile.delete();
        }

        PrintWriter writer = null;
        try {
            writer = new PrintWriter(textFile);
            writer.println("hello, this is a test....");
        } catch (FileNotFoundException e) {
            logger.error("Can not open the file");
            textFile.delete();
            assertEquals(true, false);
            return;
        } finally {
            if (null != writer) {
                writer.flush();
                writer.close();
            }
        }

        try {
            blogBackup.sendArchiveFile(filePath);
        } catch (BlogBaseException e) {
            logger.error("Sending e-mail failed!", e);
            assertEquals(true,false);
            textFile.delete();
            return;
        }

        textFile.delete();
        assertEquals(true,true);
    }


    /**
     * 测试enum的用法，valueOf：里面的string必须为enum的名称字符串
     */
    @Test
    public void testEnumValueof() throws IOException {
        BackupMethod method = BackupMethod.valueOf("EMAIL");

    }
}
