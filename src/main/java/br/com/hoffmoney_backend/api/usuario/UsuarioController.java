package br.com.hoffmoney_backend.api.usuario;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.hoffmoney_backend.modelo.usuario.Usuario;
import br.com.hoffmoney_backend.modelo.usuario.UsuarioService;
import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping("/api/usuario")
@CrossOrigin(origins = "*")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @Operation(summary = "Serviço responsável por salvar um usuário no sistema.")
    @PostMapping
    public ResponseEntity<?> save(@RequestBody Usuario request) {
        if (request.getNome() == null || request.getNome().isEmpty() ||
                request.getEmail() == null || request.getEmail().isEmpty() ||
                request.getSenha() == null || request.getSenha().isEmpty()) {
            return new ResponseEntity<>("Todos os campos são obrigatórios.", HttpStatus.BAD_REQUEST);
        }
        Usuario usuario = usuarioService.save(request);
        return new ResponseEntity<>(usuario, HttpStatus.CREATED);
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
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
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
}
