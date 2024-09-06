package br.com.hoffmoney_backend.api.usuario;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
}
