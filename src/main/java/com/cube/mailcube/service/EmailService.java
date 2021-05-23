package com.cube.mailcube.service;

import com.cube.mailcube.domain.ApplicantDto;
import com.cube.mailcube.domain.EmailRequestDto;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Properties;

@Service
@AllArgsConstructor
public class EmailService {

    private final ExcelFileService excelFileService;

    //    public List<ApplicantDto> sendEmail(String title, String content, String senderName, String senderEmail,  List<ApplicantDto> applicants) {
    public List<ApplicantDto> sendEmail(EmailRequestDto emailRequestDto, Long id) {

        List<ApplicantDto> applicants = excelFileService.getApplicants(excelFileService.getExcelFilebyId(id).get().getBlob_url());

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
            for (int i = 0; i < applicants.size(); i++) {
                String recipient_name = applicants.get(i).getName();
                String recipient_email = applicants.get(i).getEmail();

                String _title = title.replace("${name}", recipient_name);
                String _content = content.replace("${name}", recipient_name);

                MimeMessage message = new MimeMessage(session);
//                message.setFrom(new InternetAddress(user));
                message.setFrom(new InternetAddress(FROM, FROMNAME));
                message.addRecipient(Message.RecipientType.TO, new InternetAddress(recipient_email));

                // 메일 제목
                message.setSubject(_title);
                // 메일 내용
                message.setContent(_content, "text/html;charset=euc-kr");
                // send the message
                Transport.send(message);
//            System.out.println("Success Message Send");
            }
        } catch (AddressException e) {
            e.printStackTrace();
        } catch (javax.mail.MessagingException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return applicants;
    }
}
