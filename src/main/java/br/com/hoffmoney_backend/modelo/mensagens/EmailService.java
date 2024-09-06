package br.com.hoffmoney_backend.modelo.mensagens;

import java.nio.charset.StandardCharsets;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import br.com.hoffmoney_backend.modelo.usuario.Usuario;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;

@Component
public class EmailService {

    @Value("${spring.mail.username}")
    String username;

    @Value("${spring.mail.password}")
    String password;

    @Value("${spring.mail.host}")
    String host;

    @Value("${spring.mail.port}")
    int port;

    @Value("${spring.mail.properties.mail.smtp.auth}")
    String smtpAuth;

    @Value("${spring.mail.properties.mail.smtp.starttls.enable}")
    String starttls;

    @Autowired
    private TemplateEngine templateEngine;

    @Autowired
    private JavaMailSender emailSender;

    public void enviarEmailConfirmacaoCadastroUsuario(Usuario usuario) {

        String assuntoEmail = "Bem-vindo ao nosso aplicativo";

        Context params = new Context();
        params.setVariable("usuario", usuario);

        this.sendMailTemplate("usuario_cadastrado.html", usuario.getEmail(), assuntoEmail, params);
    }

    @Async
    private void sendMailTemplate(String template, String to, String subject, Context params) {

        String content = templateEngine.process(template, params);
        this.sendMail(to, subject, content, Boolean.TRUE);
    }

    @Async
    private void sendMail(String to, String subject, String content, Boolean html) {

        MimeMessage message = emailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);

        try {
            helper.setFrom(new InternetAddress("not.reply@delifacil.com.br"));
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(content, html);
            helper.setEncodeFilenames(true);

        } catch (MessagingException e) {
            e.printStackTrace();
        }

        emailSender.send(message);
    }

    public void enviarRelatorioPorEmail(String destinatario, String assunto, String corpo, byte[] pdfBytes,
            String nomeArquivoPdf) throws MessagingException {
        MimeMessage mensagem = emailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mensagem, true, StandardCharsets.UTF_8.name());

        helper.setTo(destinatario);
        helper.setSubject(assunto);
        helper.setText(corpo);

        ByteArrayResource pdfAnexo = new ByteArrayResource(pdfBytes);
        helper.addAttachment(nomeArquivoPdf, pdfAnexo);

        emailSender.send(mensagem);
    }
}
