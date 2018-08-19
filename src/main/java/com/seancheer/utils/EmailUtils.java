package com.seancheer.utils;

import com.seancheer.common.BlogConstants;
import com.seancheer.exception.BlogBaseException;
import com.seancheer.exception.SendingEmailExceptioin;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.Address;
import javax.mail.Authenticator;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimeUtility;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.util.Properties;

/**
 * 发送email的utils
 *
 * @author: seancheer
 * @date: 2018/8/9
 **/
public class EmailUtils {

    private static final Logger logger = LoggerFactory.getLogger(EmailUtils.class);

    public static final String DEFAULT_MAIL_HOST = "smtp.163.com";

    public static final String DEFAULT_MIME_TYPE = "text/html;charset=UTF-8";

    /**
     * 发送给单个人，不带文件附件
     *
     * @param from
     * @param pwd
     * @param title
     * @param to
     */
    public static void sendMsg(String from, String pwd, String title, InternetAddress to, String mailHost,
                               String content) throws SendingEmailExceptioin {
        sendMsg(from, pwd, title, new InternetAddress[]{to}, mailHost, content, null);
    }

    /**
     * 发送给单个人，带文件附件
     *
     * @param from
     * @param pwd
     * @param title
     * @param to
     */
    public static void sendMsg(String from, String pwd, String title, InternetAddress to, String mailHost
            , String content, String filePath) throws SendingEmailExceptioin {
        sendMsg(from, pwd, title, new InternetAddress[]{to}, mailHost, content, filePath);
    }


    /**
     * 发送给多个人
     *
     * @param from
     * @param pwd
     * @param title
     * @param tos
     * @param filePath
     * @param mailHost
     * @param content
     */
    public static void sendMsg(String from, String pwd, String title, InternetAddress[] tos, String mailHost,
                               String content, String filePath) throws SendingEmailExceptioin {
        if (StringUtils.isEmpty(mailHost)) {
            logger.info("Mailhost is empty! Use default host instead!");
            mailHost = DEFAULT_MAIL_HOST;
        }

        Properties props = new Properties();
        props.setProperty(BlogConstants.MAIL_HOST, mailHost);
        props.setProperty(BlogConstants.MAIL_AUTH, "true");

        //创建权限，发送人的email账号和授权码
        Authenticator authenticator = new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(from, pwd);
            }
        };

        //创建发送账号的session，由于发送邮件属于长期备份，因此这里每次都创建新的session
        Session session = Session.getDefaultInstance(props, authenticator);

        //首先设置message的基本信息
        Message message = new MimeMessage(session);

        try {
            message.setFrom(new InternetAddress(from));
            for (Address address : tos) {
                message.addRecipient(Message.RecipientType.TO, address);
            }
            message.setSubject(title);
        } catch (MessagingException e) {
            e.printStackTrace();
        }

        //检查是否有普通的文字信息，加入文字信息
        if (content == null) {
            content = "";
        }

        Multipart multipart = new MimeMultipart();
        File attachment;

        try {
            multipart.addBodyPart(genTextPart(content));
            //检查是否有文件，如果有文件，增加文件附件
            if (!StringUtils.isEmpty(filePath)) {
                attachment = new File(filePath);
                multipart.addBodyPart(genFilePart(attachment));
            }

            logger.debug("Preparing finished! Starting sending e-mail! to:{} title:{}", tos[0].getAddress(), title);
            message.setContent(multipart);
            //首先进行保存
            message.saveChanges();
            Transport.send(message);
        } catch (MessagingException | FileNotFoundException | UnsupportedEncodingException e) {
            String msg = String.format("Preparing or sending e-mail failed! to:%s title:%s", tos[0].getAddress(), title);
            logger.error(msg, e);
            throw new SendingEmailExceptioin(e, msg);
        }

        String msg = String.format("Sending e-mail success! to:%s title:%s", tos[0].getAddress(), title);
        logger.info(msg);
    }

    /**
     * 生成普通的文字对象
     *
     * @param content
     * @return
     */
    private static BodyPart genTextPart(String content) throws MessagingException {
        BodyPart textPart = new MimeBodyPart();
        textPart.setContent(content, DEFAULT_MIME_TYPE);
        return textPart;
    }

    /**
     * 生成文件相关的part
     *
     * @param file
     * @return
     */
    private static BodyPart genFilePart(File file) throws FileNotFoundException, MessagingException, UnsupportedEncodingException {
        if (!file.exists()) {
            throw new FileNotFoundException("File not exits! fileName:" + file);
        }

        BodyPart filePart = new MimeBodyPart();
        DataSource dataSource = new FileDataSource(file);
        filePart.setDataHandler(new DataHandler(dataSource));
        filePart.setFileName(MimeUtility.encodeWord(file.getName()));
        return filePart;
    }
}
