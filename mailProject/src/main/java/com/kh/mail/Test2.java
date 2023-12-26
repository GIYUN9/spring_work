package com.kh.mail;

import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@Controller
public class Test2 {
	
	@Autowired
	private JavaMailSender sender;
	
	@RequestMapping(value = "send")
	public String mail() {
		//메세지 생성
		SimpleMailMessage message = new SimpleMailMessage();
		message.setSubject("이메일 전송 테스트(제목)2");
		message.setText("이메일 전송 테스트 두번째입니다.(내용)2");
		
		String[] to = {"이메일주소"};
		message.setTo(to);
		
		String[] cc = {"이메일주소"};
		message.setCc(cc);
		
		sender.send(message);
		
		return "redirect:/";
	}
	
	@RequestMapping(value = "hypermail")
	public String hypermail() throws MessagingException {
		//메일을 보내는 표준양식 MIME를 통해서 메일을 전송해보자
		MimeMessage message = sender.createMimeMessage();
		
		MimeMessageHelper helper = new MimeMessageHelper(message, false, "UTF-8");
		
		String[] to = {"이메일주소"};
		helper.setTo(to);
		
		String[] cc = {"이메일주소"};
		helper.setCc(cc);
		
		helper.setSubject("이메일 전송 테스트(제목)3");
		//두번째 인자를 true로 보낼 시 html 사용하겠다라는 의미
		String url = ServletUriComponentsBuilder
				.fromCurrentContextPath()
				.port(8010).path("/product")
				.queryParam("product_id", 1002)
				.toUriString();
				
		helper.setText("<a href='"+ url + "'>웹사이트로 이동</a>", true);
		
		sender.send(message);
		return "redirect:/";
	}
	
	@RequestMapping(value = "sendfile")
	public String sendfile() throws MessagingException {
		//메일을 보내는 표준양식 MIME를 통해서 메일을 전송해보자
		MimeMessage message = sender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
		
		String[] to = {"이메일주소"};
		helper.setTo(to);
		
		String[] cc = {"이메일주소"};
		helper.setCc(cc);
		
		helper.setSubject("이메일 전송 테스트(제목)4");
		//두번째 인자를 true로 보낼 시 html 사용하겠다라는 의미
				
		
		helper.setText("파일 전송합니다");
		
		//첨부파일 추가
		// javax.activation.DataSources : 파일정보
		DataSource source = new FileDataSource("C:\\Users\\user1\\Downloads\\이미지저장용테스트빵빵이.jpg");
		helper.addAttachment(source.getName(), source);
		
		sender.send(message);
		return "redirect:/";
	}
}
