package com.belonk.commons.util.mail;

import org.apache.commons.mail.EmailAttachment;
import org.apache.commons.mail.MultiPartEmail;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * emailsubject  邮件主题
 * toUserName  接收方邮件账号
 * attachmentPath 附件地址
 * attachmentPathName 附件名称
 * mailMSg 邮件文本内容
 * Created by xiahuaze on 2018/1/18.
 */
public class SendMail {
    public static void sendAttachmentMail(String emailsubject, String toUserName, List attachmentPaths, String mailMSg) throws Exception {
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

        attachment.setDisposition(EmailAttachment.ATTACHMENT);
        for (int i = 0; i < attachmentPaths.size(); i++) {
            String attachmentPath = (String) attachmentPaths.get(i);
            attachment.setPath(attachmentPath);
            mail.attach(attachment);
            String fileNameWithSuffix = getFileNameWithSuffix("attachmentPath");
            attachment.setName(fileNameWithSuffix);
        }
        // 设置邮件发送时间
        mail.setSentDate(new Date());
        // 发送邮件
        mail.send();
    }

    /**
     * 保留文件名及后缀
     */
    public static String getFileNameWithSuffix(String pathandname) {
        int start = pathandname.lastIndexOf("/");
        if (start != -1) {
            return pathandname.substring(start + 1);
        } else {
            return null;
        }
    }

    public static void main(String[] args) {
        List<String> paths = new ArrayList<>();
        paths.add("C:\\data\\QQ截图20180123171318.png");
        paths.add("C:\\data\\QQ截图20180123161502.png");
        try {
            new SendMail().sendAttachmentMail("fdsafdsaf", "397514930@qq.com", paths, "fdsafda");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
