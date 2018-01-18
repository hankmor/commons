package com.belonk.commons.util.mail;

import org.apache.commons.mail.EmailAttachment;
import org.apache.commons.mail.MultiPartEmail;

import java.util.Date;

/**
 *
 * host  邮件服务地址 ，如：smtp.qq.com
 * port  邮件服务端口
 * userName 发送人的邮箱账号
 * password  发送人的邮箱密码
 * emailsubject  邮件主题
 * toUserName  接收方邮件账号
 * attachmentPath 附件地址
 * attachmentPathName 附件名称
 * mailMSg 邮件文本内容
 * Created by xiahuaze on 2018/1/18.
 */
public class SendMail {
    public void sendAttachmentMail(String host,int port,String userName,String password,String emailsubject,String toUserName,String attachmentPath,String attachmentPathName,String mailMSg) throws Exception {
        MultiPartEmail mail = new MultiPartEmail();
        // 设置邮箱服务器信息
        mail.setSmtpPort(port);
        mail.setHostName(host);
        // 设置密码验证器
        mail.setAuthentication(userName, password);
        mail.setSSLOnConnect(true);
        // 设置邮件发送者
        mail.setFrom(userName);
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
}
