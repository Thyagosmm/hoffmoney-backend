package br.com.hoffmoney_backend.api.usuario;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import br.com.hoffmoney_backend.modelo.mensagens.EmailService;
import br.com.hoffmoney_backend.modelo.usuario.Usuario;
import br.com.hoffmoney_backend.modelo.usuario.UsuarioRepository;
import br.com.hoffmoney_backend.modelo.usuario.UsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/usuario")
@CrossOrigin(origins = "*")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private EmailService emailService;

    @Operation(summary = "Serviço responsável por salvar um usuário no sistema.")
    @PostMapping
    public ResponseEntity<?> save(@RequestBody @Valid UsuarioRequest usuarioRequest) {
        if (usuarioRepository.existsByEmail(usuarioRequest.getEmail())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Este e-mail já está cadastrado.");
        } else {
            Usuario usuario = usuarioService.save(usuarioRequest.build());
            return new ResponseEntity<>(usuario, HttpStatus.CREATED);
        }
    }

    @Operation(summary = "Serviço responsável por listar todos os usuários do sistema.")
    @GetMapping
    public List<Usuario> listAllUsers() {
        return usuarioService.listarTodos();
    }

    @Operation(summary = "Serviço responsável por listar um usuário específico do sistema.")
    @GetMapping("/{id}")
    public Usuario getUserById(@PathVariable Long id) {
        return usuarioService.obterPorID(id);
    }

    @Operation(summary = "Serviço responsável por alterar dados de um usuário específico do sistema.")
    @PutMapping("/{id}")
    public ResponseEntity<Usuario> updateUser(@PathVariable("id") Long id, @RequestBody Usuario request) {
        usuarioService.update(id, request);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Serviço responsável por excluir um usuário específico do sistema.")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        usuarioService.delete(id);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        try {
            Usuario usuario = usuarioService.login(loginRequest.getEmail(), loginRequest.getSenha());

            if (usuario == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Usuário não encontrado");
            }

            if (!usuario.getSenha().equals(loginRequest.getSenha())) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Senha incorreta");
            }
            if (!usuario.getHabilitado()) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Conta não ativada. Verifique seu e-mail.");
            }

            Usuario logado = new Usuario();
            logado.setId(usuario.getId());
            logado.setNome(usuario.getNome());
            logado.setEmail(usuario.getEmail());

            return ResponseEntity.ok(logado);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        }
    }

    @Operation(summary = "Incrementar saldo da carteira do usuário.")
    @PutMapping("/incrementar")
    public ResponseEntity<?> incrementarSaldo(@RequestBody SaldoRequest saldoRequest) {

        Long id = saldoRequest.getId();
        Double valor = saldoRequest.getValor();

        usuarioService.incrementarSaldo(id, valor);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Decrementar saldo da carteira do usuário.")
    @PutMapping("/decrementar")
    public ResponseEntity<?> decrementarSaldo(@RequestBody SaldoRequest saldoRequest) {

        Long id = saldoRequest.getId();
        Double valor = saldoRequest.getValor();

        usuarioService.decrementarSaldo(id, valor);
        return ResponseEntity.ok().build();

    }

    @GetMapping("/saldo")
    public ResponseEntity<?> consultarSaldo(@RequestParam Long id) {
        Double saldo = usuarioService.obterPorID(id).getSaldo();
        return ResponseEntity.ok(saldo);
    }

    @GetMapping("/{id}/limite")
    public ResponseEntity<?> consultarLimite(@PathVariable Long id) {
        Double limite = usuarioService.obterPorID(id).getLimite();
        return ResponseEntity.ok(limite);
    }

    @PutMapping("/{id}/limite")
    public ResponseEntity<Void> atualizarLimite(@PathVariable Long id, @RequestBody Double novoLimite) {
        usuarioService.atualizarLimite(id, novoLimite);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    // Endpoint para solicitar recuperação de senha
    @Operation(summary = "Solicitar recuperação de senha.")
    @PostMapping("/reset-password")
    public ResponseEntity<?> resetPassword(@RequestParam String email) {
        Usuario usuario = usuarioRepository.findByEmail(email);

        if (usuario == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuário não encontrado.");
        }

        // Gerar token de recuperação de senha
        String token = usuarioService.gerarTokenRecuperacao(usuario);

        // Montar o link de recuperação de senha
        String link = "http://localhost:3000/reset?token=" + token;  // Alterar conforme o URL do frontend

        // Enviar e-mail de redefinição de senha
        emailService.enviarEmailRedefinicaoSenha(usuario.getEmail(), link);

        return ResponseEntity.ok("E-mail de redefinição enviado com sucesso.");
    }

    // Endpoint para redefinir a senha usando o token
    @Operation(summary = "Redefinir senha usando o token.")
    @PostMapping("/redefinir-senha")
    public ResponseEntity<?> redefinirSenha(@RequestParam String token, @RequestParam String novaSenha) {
        try {
            // Validar o token e obter o usuário
            Usuario usuario = usuarioService.validarTokenRecuperacao(token);

            // Redefinir a senha do usuário
            usuario.setSenha(novaSenha);
            usuario.setResetToken(null);  // Limpar o token
            usuario.setResetTokenExpiry(null);  // Limpar a expiração do token

            // Atualizar o usuário com a nova senha
            usuarioService.update(usuario.getId(), usuario);

            return ResponseEntity.ok("Senha redefinida com sucesso.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Token inválido ou expirado.");
        }
    }
    @Operation(summary = "Ativar conta usando o token.")
    @GetMapping("/ativar")
    public ResponseEntity<?> ativarConta(@RequestParam String token) {
        try {
            // Validar o token e obter o usuário
            Usuario usuario = usuarioService.validarTokenAtivacao(token);
            if (usuario.getHabilitado()) {
                return ResponseEntity.ok("Conta ativada com sucesso.");
            }
            // Ativar a conta do usuário
            usuarioService.ativarConta(usuario);

            return ResponseEntity.ok("Conta ativada com sucesso.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}