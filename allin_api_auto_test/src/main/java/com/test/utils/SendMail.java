package com.test.utils;

import javax.mail.*;
import javax.mail.internet.*;
import java.util.Date;
import java.util.Properties;

public class SendMail {
	private static String from = String.valueOf(YamlConfig.instance.getValueByKey("email.account"));// 发件人的邮箱地址
	private static String user = String.valueOf(YamlConfig.instance.getValueByKey("email.account"));// 发件人称号，同邮箱地址
	private static String password = String.valueOf(YamlConfig.instance.getValueByKey("email.password"));// 发件人邮箱客户端的授权码

	/* 发送验证信息的邮件 */
	public static boolean sendMail() {
		Properties props = new Properties();
		props.setProperty("mail.smtp.host", "smtp.163.com"); // 设置发送邮件的邮件服务器的属性（这里使用网易的smtp服务器）
		props.put("mail.smtp.host", "smtp.163.com"); // 需要经过授权，也就是有户名和密码的校验，这样才能通过验证（一定要有这一条）
		props.put("mail.smtp.auth", "true"); // 用刚刚设置好的props对象构建一个session
		Session session = Session.getDefaultInstance(props); // 有了这句便可以在发送邮件的过程中在console处显示过程信息，供调试使
		// 用（你可以在控制台（console)上看到发送邮件的过程）
		session.setDebug(true); // 用session为参数定义消息对象
		MimeMessage message = new MimeMessage(session); // 加载发件人地址
		try {
			message.setFrom(new InternetAddress(from));
			InternetAddress[] internetAddressTo = InternetAddress.parse(String.valueOf(YamlConfig.instance.getValueByKey("email.receiverList")));
			message.setRecipients(Message.RecipientType.TO, internetAddressTo);
			message.setSubject(String.valueOf(YamlConfig.instance.getValueByKey("email.subject"))); // 加载标题
			Multipart multipart = new MimeMultipart(); // 向multipart对象中添加邮件的各个部分内容，包括文本内容和附件
			BodyPart contentPart = new MimeBodyPart(); // 设置邮件的文本内容
			contentPart.setContent("<h3 style=\"text-align:center;\"><br>自动化测试报告地址：<br/></h3><p style=\"text-align:center;\">http://192.168.112.66:8081/report/index.html</p>", "text/html;charset=utf-8");
			multipart.addBodyPart(contentPart);
			message.setContent(multipart);
			message.saveChanges(); // 保存变化
			Transport transport = session.getTransport("smtp"); // 连接服务器的邮箱
			transport.connect("smtp.163.com", user, password); // 把邮件发送出去
			transport.sendMessage(message, message.getAllRecipients());
			transport.close();
		} catch (MessagingException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
}
