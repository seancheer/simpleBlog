package com.seancheer.utils;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

/**
 * IOUtils，将InputStream转换为byte[]数组
 * 
 * @author seancheer
 * @date 2018年4月11日
 */
public class IOUtils {

	private static final Logger logger = LoggerFactory.getLogger(IOUtils.class);

	private static final int TMP_SIZE = 10;

	private static final int BUF_SIZE = 10 * TMP_SIZE;

	private static final int DEFAULT_SLEEP_MS = 100;

	/**
	 * 将inputstream转换为string，根据传入的charSet
	 * 
	 * @param input
	 * @param charSet
	 * @return
	 * @throws IOException
	 * @throws InterruptedException
	 */
	public static String convertStreamToString(InputStream input, String charSet)
			throws IOException, InterruptedException {
		if (null == input || StringUtils.isEmpty(charSet)) {
			logger.error("Invalid input or charset! input:{} charSet:{}", input, charSet);
			throw new IllegalArgumentException("Invalid input or charset! Please check!");
		}

		byte[] tmp = new byte[TMP_SIZE];
		int bytesRead = 0;

		byte[] result = new byte[BUF_SIZE];
		int curSize = 0;
		int totalSize = BUF_SIZE;

		try {
			while ((bytesRead = input.read(tmp)) >= 0) {
				if (0 == bytesRead) {
					TimeUnit.MILLISECONDS.sleep(DEFAULT_SLEEP_MS);
					continue;
				}
				
				//说明此时result数据大小不够了，因此需要扩充result数组
				if (curSize + bytesRead > totalSize) {
					result = Arrays.copyOf(result, curSize + BUF_SIZE);
				}
				
				System.arraycopy(tmp, 0, result, curSize, bytesRead);
				curSize += bytesRead;
			}
		} catch (IOException e) {
			logger.error("Read data failed!", e);
			throw e;
		} catch (InterruptedException e) {
			logger.error("InterruptedException!", e);
			throw e;
		}
		return new String(result, 0, curSize, charSet);
	}


	/**
	 * 将in中的copy到out中，注意，该方法不负责inputstream的close，请自行进行close
	 * @param out
	 * @param in
	 */
	public static void copyStream(OutputStream out, InputStream in) throws IOException {
        byte[] buffer = new byte[BUF_SIZE];
        int bytesRead = 0;

        try {
            while ((bytesRead = in.read(buffer)) >= 0) {
                if (0 == bytesRead) {
                    TimeUnit.MILLISECONDS.sleep(DEFAULT_SLEEP_MS);
                    continue;
                }

                out.write(buffer, 0, bytesRead);
                out.flush();
            }
        } catch (InterruptedException e) {
            logger.error("InterruptedException", e);
            throw new IOException(e);
        }
    }
}
