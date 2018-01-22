package com.belonk.commons.util.mail;

import org.apache.commons.mail.EmailAttachment;
import org.apache.commons.mail.MultiPartEmail;

import java.util.Date;

/**
 *
 * emailsubject  邮件主题
 * toUserName  接收方邮件账号
 * attachmentPath 附件地址
 * attachmentPathName 附件名称
 * mailMSg 邮件文本内容
 * Created by xiahuaze on 2018/1/18.
 */
public class SendMail {
    public static void sendAttachmentMail(String emailsubject,String toUserName,String attachmentPath,String attachmentPathName,String mailMSg) throws Exception {
        MultiPartEmail mail = new MultiPartEmail();
        // 设置邮箱服务器信息
        mail.setSmtpPort(465);
        mail.setHostName("smtp.mxhichina.com");
        // 设置密码验证器
        mail.setAuthentication("info@abcbooking.cn", "Airbc121212");
        mail.setSSLOnConnect(true);
        // 设置邮件发送者
        mail.setFrom("info@abcbooking.cn");
        // 设置邮件接收者
        mail.addTo(toUserName);
        // 设置邮件编码
        mail.setCharset("UTF-8");
        // 设置邮件主题
        mail.setSubject(emailsubject);
        mail.setMsg(mailMSg);
        // 创建附件
        EmailAttachment attachment = new EmailAttachment();
        attachment.setPath(attachmentPath);
        attachment.setDisposition(EmailAttachment.ATTACHMENT);
        attachment.setName(attachmentPathName);
        mail.attach(attachment);

        // 设置邮件发送时间
        mail.setSentDate(new Date());
        // 发送邮件
        mail.send();
    }

    public static void main(String[] args) {
        try {
            new SendMail().sendAttachmentMail("fdsafdsaf","397514930@qq.com","C:\\data\\app推广二维码.png","pp推广二维码.png","fdsafda");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
