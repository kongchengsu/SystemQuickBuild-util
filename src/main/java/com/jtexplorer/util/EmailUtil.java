package com.jtexplorer.util;


import com.jtexplorer.config.ParamStaticConfig;
import com.sun.mail.util.MailSSLSocketFactory;
import lombok.extern.slf4j.Slf4j;

import javax.mail.*;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.security.GeneralSecurityException;
import java.security.Security;
import java.util.Date;
import java.util.Properties;


/**
 * Created by lenovo on 2016/8/23.
 */
@Slf4j
public class EmailUtil {
    /**
     * 发送email
     *
     * @param host           邮件服务器（若用163邮箱发送，smtp.163.com.若用qq邮箱发送，smtp.qq.com其他自行百度）
     * @param Addressee      收件人邮箱(可以为多个邮箱需用“;”分隔开用于实现群发)
     * @param title          标题
     * @param context        内容
     * @param sender         发件人邮箱（需开通pop3/smtp服务）
     * @param senderpassword 发件人开通pop3/smtp后的密码（授权码）
     */
    private static final String host = ParamStaticConfig.getWebappPathStatic(ParamStaticConfig.ConfigParamKeyEnum.emailHost).toString();
    private static final String sender = ParamStaticConfig.getWebappPathStatic(ParamStaticConfig.ConfigParamKeyEnum.emailSender).toString();
    private static final String senderpassword = ParamStaticConfig.getWebappPathStatic(ParamStaticConfig.ConfigParamKeyEnum.emailSenderPassword).toString();
    private static final String emailtitle = ParamStaticConfig.getWebappPathStatic(ParamStaticConfig.ConfigParamKeyEnum.emailTitle).toString();


    /**
     * 使用加密的方式,利用465端口进行传输邮件,开启ssl
     *
     * @param Addressee 为收件人邮箱
     * @param context   发送的消息
     */
    public static boolean sendEmail(String Addressee, String context) {
        try {
            Security.addProvider(new com.sun.net.ssl.internal.ssl.Provider());
            final String SSL_FACTORY = "javax.net.ssl.SSLSocketFactory";
            // Get a Properties object
            Properties props = System.getProperties();
            MailSSLSocketFactory sf = null;
            sf = new MailSSLSocketFactory();
            sf.setTrustAllHosts(true);
            // or
            // sf.setTrustedHosts(new String[] { "my-server" });
            props.put("mail.smtp.ssl.enable", "true");
            // also use following for additional safety
            //props.put("mail.smtp.ssl.checkserveridentity", "true");
            props.put("mail.smtp.ssl.socketFactory", sf);
            props.setProperty("mail.smtp.host", host);//host邮件服务器
            props.setProperty("mail.smtp.socketFactory.class", SSL_FACTORY);
            props.setProperty("mail.smtp.socketFactory.fallback", "false");
            props.put("mail.transport.protocol", "smtp");
            props.setProperty("mail.smtp.port", "465");
            props.setProperty("mail.smtp.socketFactory.port", "465");
            props.put("mail.smtp.auth", "true");
            final String username = sender;//发送者邮箱
            final String password = senderpassword;//发送者密码
            Session session = Session.getDefaultInstance(props,
                    new Authenticator() {
                        @Override
                        protected PasswordAuthentication getPasswordAuthentication() {
                            return new PasswordAuthentication(username, password);
                        }
                    });
            Message msg = new MimeMessage(session);
            msg.setFrom(new InternetAddress(username));
            msg.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(Addressee, false));
            msg.setSubject(emailtitle);
          //  msg.setText(context);
            msg.setContent(context,"text/html;charset = utf-8");
            msg.setSentDate(new Date());
            Transport.send(msg);
//            System.out.println("Message sent.");
            return true;
        } catch (GeneralSecurityException e) {
            e.printStackTrace();
            return false;
        } catch (AddressException e) {
            e.printStackTrace();
            return false;
        } catch (MessagingException e) {
            e.printStackTrace();
            return false;
        }
    }
}
