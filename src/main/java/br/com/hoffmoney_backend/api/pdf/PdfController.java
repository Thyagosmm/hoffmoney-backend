package br.com.hoffmoney_backend.api.pdf;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import br.com.hoffmoney_backend.modelo.mensagens.EmailService;
import jakarta.mail.MessagingException;

@RestController
@RequestMapping("/api/pdf")
public class PdfController {

    @Autowired
    private EmailService emailService;

    @PostMapping("/enviar")
    public ResponseEntity<String> enviarPdfPorEmail(
            @RequestParam("email") String email,
            @RequestParam("assunto") String assunto,
            @RequestParam("corpo") String corpo,
            @RequestParam("arquivoPdf") MultipartFile arquivoPdf) {
        try {
            emailService.enviarRelatorioPorEmail(
                    email,
                    assunto,
                    corpo,
                    arquivoPdf.getBytes(),
                    arquivoPdf.getOriginalFilename());
            return ResponseEntity.ok("E-mail enviado com sucesso!");
        } catch (MessagingException | IOException e) {
            return ResponseEntity.status(500).body("Erro ao enviar o e-mail: " + e.getMessage());
        }
    }
}
