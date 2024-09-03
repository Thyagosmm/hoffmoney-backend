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

        return receitaRepository.save(receita);
    }

    @Transactional
    public void atualizarReceita(Long id, Receita receitaAtualizada) {
        Receita receita = receitaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Receita não encontrada"));
        receita.setCategoriaReceita(receitaAtualizada.getCategoriaReceita());
        receita.setNome(receitaAtualizada.getNome());
        receita.setDescricao(receitaAtualizada.getDescricao());
        receita.setValor(receitaAtualizada.getValor());
        receita.setDataDeCobranca(receitaAtualizada.getDataDeCobranca());
        receita.setPaga(receitaAtualizada.getPaga());
        receita.setVersao(receita.getVersao() + 1);
        receitaRepository.save(receita);
    }

    @Transactional
    public void deletarReceita(Long id, Long usuarioId) {
        receitaRepository.findByIdAndUsuarioId(id, usuarioId)
                .orElseThrow(() -> new RuntimeException("Receita não encontrada"));

        receitaRepository.deleteById(id);
    }

    @Transactional
    public void atualizarPaga(Long id, Boolean novaSituacaoPaga) {
        Receita receita = receitaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Receita não encontrada"));

        receita.setPaga(novaSituacaoPaga);
        receitaRepository.save(receita);
    }

    @Transactional
    public List<Receita> filtrar(LocalDate dataDeCobranca, Double valor, Long categoriaDespesa, String nome,
            Long usuarioId) {
        return receitaRepository.filtrarReceitas(dataDeCobranca, valor, categoriaDespesa, nome, usuarioId);
    }
}
