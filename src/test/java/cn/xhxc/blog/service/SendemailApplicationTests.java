package cn.xhxc.blog.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.test.context.junit4.SpringRunner;

import cn.xhxc.blog.mail.IMailService;

@SpringBootTest
public class SendemailApplicationTests {

	@Autowired
    private JavaMailSender mailSender;
	
	 /**
     * 注入发送邮件的接口
     */
    @Autowired
    private IMailService mailService;

    @Test
    public void sendSimpleMail() throws Exception {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("xinghxc@qq.com");
        message.setTo("ly1815201984@163.com");
        message.setSubject("主题：简单邮件");
        message.setText("测试邮件内容");

        mailSender.send(message);
    }
    
    /**
     * 测试发送文本邮件
     */
    @Test
    public void sendmail() {
        mailService.sendSimpleMail("ly1815201984@163.com","主题：你好普通邮件","内容：第一封邮件");
    }

    @Test
    public void sendmailHtml(){
        mailService.sendHtmlMail("ly1815201984@163.com","主题：你好html邮件","<h1>内容：第一封html邮件</h1>");
    }
}