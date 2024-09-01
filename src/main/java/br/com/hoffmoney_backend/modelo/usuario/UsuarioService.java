package br.com.hoffmoney_backend.modelo.usuario;

import java.time.LocalDate;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.hoffmoney_backend.modelo.mensagens.EmailService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

@Service
public class UsuarioService {

    @Autowired
    private EmailService emailService;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Transactional
    public Usuario save(Usuario usuario) {

        usuario.setHabilitado(Boolean.TRUE);
        usuario.setVersao(1L);
        usuario.setDataCriacao(LocalDate.now());

        // Comentar a linha abaixo quando não quiser mandar e-mail
       // emailService.enviarEmailConfirmacaoCadastroUsuario(usuario);

        return usuarioRepository.save(usuario);
    }

    @Transactional
    public List<Usuario> listarTodos() {
        return usuarioRepository.findAll();
    }

    @Transactional
    public Usuario obterPorID(Long id) {
        return usuarioRepository.findById(id).orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
    }

    public Usuario findById(Long id) {
        return usuarioRepository.findById(id).orElse(null);
    }

    @Transactional
    public void update(Long id, Usuario usuarioAlterado) {
        Usuario usuario = obterPorID(id);
        usuario.setNome(usuarioAlterado.getNome());
        usuario.setEmail(usuarioAlterado.getEmail());
        usuario.setSenha(usuarioAlterado.getSenha());
        usuario.setVersao(usuario.getVersao() + 1);
        usuarioRepository.save(usuario);
    }

    @Transactional
    public void delete(Long id) {
        Usuario usuario = obterPorID(id);
        usuario.setHabilitado(Boolean.FALSE);
        usuario.setVersao(usuario.getVersao() + 1);
        usuarioRepository.save(usuario);
    }

    public Usuario login(String email, String senha) {
        Usuario usuario = usuarioRepository.findByEmail(email);
        System.out.println(usuario);
        if (usuario != null && usuario.getSenha().equals(senha)) {
            return usuario;
        }
        throw new IllegalArgumentException("Email ou senha inválidos");
    }

    @Transactional
    public void decrementarSaldo(Long usuarioId, Double valor) {
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado para o ID especificado"));

        Double novoSaldo = usuario.getSaldo() - valor;
        if (novoSaldo < 0) {
            throw new IllegalArgumentException("Saldo insuficiente");
        }

        usuario.setSaldo(novoSaldo);
        usuarioRepository.save(usuario);
    }

    @Transactional
    public void incrementarSaldo(Long usuarioId, Double valor) {
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado para o ID especificado"));

        Double novoSaldo = usuario.getSaldo() + valor;
        usuario.setSaldo(novoSaldo);
        usuarioRepository.save(usuario);
    }
}