package com.seancheer.backupservice;


import com.seancheer.exception.BlogBaseException;
import junit.framework.TestCase;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

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
            return;
        }

        assertEquals(true,true);
    }
}
