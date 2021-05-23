package com.cube.mailcube.service;

import com.cube.mailcube.domain.ApplicantDto;
import com.cube.mailcube.domain.EmailRequestDto;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class EmailService {

	private final ExcelFileService excelFileService;

	public boolean sendEmail(EmailRequestDto emailRequestDto, List<ApplicantDto> applicants) {
		String title = emailRequestDto.getTitle();
		String content = emailRequestDto.getContent();
		String senderName = emailRequestDto.getSenderName();
		String senderEmail = emailRequestDto.getSenderEmail();

		String host = "smtp.gmail.com";
		String user = "gimquokka@gmail.com";
		String password = "rlawlsdud*5097";

		String FROM = senderEmail;
		String FROMNAME = senderName;

		Properties props = new Properties();
		props.put("mail.smtp.host", host);
		props.put("mail.smtp.port", 465);
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.ssl.trust", host);
		props.put("mail.smtp.ssl.enable", "true");

		Session session = Session.getDefaultInstance(props, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(user, password);
			}
		});

		try {
			for (ApplicantDto applicant : applicants) {
				String recipient_name = applicant.getName();
				String recipient_email = applicant.getEmail();

                if (!recipient_email.contains("@")) {
                    break;
                }

				MimeMessage message = new MimeMessage(session);
				message.setFrom(new InternetAddress(user));
				message.setFrom(new InternetAddress(senderEmail, FROMNAME));
				message
					.addRecipient(Message.RecipientType.TO, new InternetAddress(recipient_email));

				String _content = content.replace("${name}", recipient_name);
				String _title = title.replace("${name}", recipient_name);

				message.setSubject(_title);
				message.setContent(_content, "text/html;charset=euc-kr");
				Transport.send(message);
			}
		} catch (MessagingException | UnsupportedEncodingException e) {
			System.err.println(e.getMessage());
			return false;
		}
		return true;
	}
}
