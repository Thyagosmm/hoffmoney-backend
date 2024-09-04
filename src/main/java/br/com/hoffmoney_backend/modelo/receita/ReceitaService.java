package br.com.hoffmoney_backend.modelo.receita;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.hoffmoney_backend.modelo.categoriareceita.CategoriaReceita;
import br.com.hoffmoney_backend.modelo.categoriareceita.CategoriaReceitaRepository;
import br.com.hoffmoney_backend.modelo.usuario.Usuario;
import br.com.hoffmoney_backend.modelo.usuario.UsuarioRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

@Service
public class ReceitaService {

    @Autowired
    private ReceitaRepository receitaRepository;

    @Autowired
    private CategoriaReceitaRepository categoriaReceitaRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Transactional
    public List<Receita> listarTodasReceitas() {
        return receitaRepository.findAll();
    }

    @Transactional
    public List<Receita> listarReceitasPorUsuarioId(Long usuarioId) {
        return receitaRepository.findByUsuarioId(usuarioId);
    }

    @Transactional
    public Optional<Receita> consultarReceitaPorIdEUsuarioId(Long id, Long usuarioId) {
        return receitaRepository.findByIdAndUsuarioId(id, usuarioId);
    }

    @Transactional
    public Receita criarReceita(Receita receita) {
        Usuario usuario = usuarioRepository.findById(receita.getUsuario().getId())
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        CategoriaReceita categoria = categoriaReceitaRepository.findById(receita.getCategoriaReceita().getId())
                .orElseThrow(() -> new RuntimeException("Categoria não encontrada"));

        receita.setUsuario(usuario);
        receita.setCategoriaReceita(categoria);

        Receita novaReceita = receitaRepository.save(receita);

        usuario.setSaldo(usuario.getSaldo() + novaReceita.getValor());
        usuarioRepository.save(usuario);

        return novaReceita;
    }

    @Transactional
    public void atualizarReceita(Long id, Receita receitaAtualizada) {
        Receita receita = receitaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Receita não encontrada"));

        double valorAntigo = receita.getValor();
        double valorNovo = receitaAtualizada.getValor();
        double diferenca = valorNovo - valorAntigo;

        receita.setCategoriaReceita(receitaAtualizada.getCategoriaReceita());
        receita.setNome(receitaAtualizada.getNome());
        receita.setDescricao(receitaAtualizada.getDescricao());
        receita.setValor(valorNovo);
        receita.setDataDeCobranca(receitaAtualizada.getDataDeCobranca());
        receita.setPaga(receitaAtualizada.getPaga());
        receita.setVersao(receita.getVersao() + 1);
        receitaRepository.save(receita);

        Usuario usuario = receita.getUsuario();
        usuario.setSaldo(usuario.getSaldo() + diferenca);
        usuarioRepository.save(usuario);
    }

    @Transactional
    public void deletarReceita(Long id, Long usuarioId) {
        Receita receita = receitaRepository.findByIdAndUsuarioId(id, usuarioId)
                .orElseThrow(() -> new RuntimeException("Receita não encontrada"));

        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        usuario.setSaldo(usuario.getSaldo() - receita.getValor());
        usuarioRepository.save(usuario);
        receitaRepository.deleteById(id);
    }

    @Transactional
    public void atualizarPaga(Long receitaId, Boolean novaSituacaoPaga) {
        Receita receita = receitaRepository.findById(receitaId)
                .orElseThrow(() -> new EntityNotFoundException("Receita não encontrada para o ID especificado"));

        Usuario usuario = receita.getUsuario();

        if (novaSituacaoPaga && !receita.getPaga()) {
            usuario.setSaldo(usuario.getSaldo() + receita.getValor());
        } else if (!novaSituacaoPaga && receita.getPaga()) {
            usuario.setSaldo(usuario.getSaldo() - receita.getValor());
        }

        receita.setPaga(novaSituacaoPaga);
        receitaRepository.save(receita);
        usuarioRepository.save(usuario);
    }

    @Transactional
    public List<Receita> filtrar(LocalDate dataDeCobranca, Double valor, Long categoriaDespesa, String nome,
            Long usuarioId) {
        return receitaRepository.filtrarReceitas(dataDeCobranca, valor, categoriaDespesa, nome, usuarioId);
    }
}
