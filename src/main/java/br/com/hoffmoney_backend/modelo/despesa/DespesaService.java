package br.com.hoffmoney_backend.modelo.despesa;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.hoffmoney_backend.modelo.categoriadespesa.CategoriaDespesa;
import br.com.hoffmoney_backend.modelo.categoriadespesa.CategoriaDespesaRepository;
import br.com.hoffmoney_backend.modelo.usuario.Usuario;
import br.com.hoffmoney_backend.modelo.usuario.UsuarioRepository;
import jakarta.transaction.Transactional;

@Service
public class DespesaService {

    @Autowired
    private DespesaRepository despesaRepository;

    @Autowired
    private CategoriaDespesaRepository categoriaDespesaRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Transactional
    public List<Despesa> listarTodasDespesas() {
        return despesaRepository.findAll();
    }

    @Transactional
    public List<Despesa> listarDespesasPorUsuarioId(Long usuarioId) {
        return despesaRepository.findByUsuarioId(usuarioId);
    }

    @Transactional
    public Optional<Despesa> consultarDespesaPorIdEUsuarioId(Long id, Long usuarioId) {
        return despesaRepository.findByIdAndUsuarioId(id, usuarioId);
    }

    @Transactional
    public Despesa criarDespesa(Despesa despesa) {
        Usuario usuario = usuarioRepository.findById(despesa.getUsuario().getId())
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        CategoriaDespesa categoria = categoriaDespesaRepository.findById(despesa.getCategoriaDespesa().getId())
                .orElseThrow(() -> new RuntimeException("Categoria não encontrada"));

        despesa.setUsuario(usuario);
        despesa.setCategoriaDespesa(categoria);

        return despesaRepository.save(despesa);
    }

    @Transactional
    public void atualizarDespesa(Long id, Despesa despesaAtualizada) {
        Despesa despesa = despesaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Despesa não encontrada"));
        despesa.setCategoriaDespesa(despesaAtualizada.getCategoriaDespesa());
        despesa.setNome(despesaAtualizada.getNome());
        despesa.setDescricao(despesaAtualizada.getDescricao());
        despesa.setValor(despesaAtualizada.getValor());
        despesa.setDataDeCobranca(despesaAtualizada.getDataDeCobranca());
        despesa.setPaga(despesaAtualizada.getPaga());
        despesa.setVersao(despesa.getVersao() + 1);
        despesaRepository.save(despesa);
    }

    @Transactional
    public void deletarDespesa(Long id, Long usuarioId) {
        Despesa despesa = despesaRepository.findByIdAndUsuarioId(id, usuarioId)
                .orElseThrow(() -> new RuntimeException("Despesa não encontrada"));

        despesaRepository.deleteById(id);
    }

    @Transactional
    public void atualizarPaga(Long id, Boolean novaSituacaoPaga) {
        Despesa despesa = despesaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Despesa não encontrada"));

        despesa.setPaga(novaSituacaoPaga);
        despesaRepository.save(despesa);
    }

    @Transactional
    public List<Despesa> filtrar(LocalDate dataDeCobranca, Double valor, Long categoriaDespesa, String nome,
            Long usuarioId) {
        return despesaRepository.filtrarDespesas(dataDeCobranca, valor, categoriaDespesa, nome, usuarioId);
    }
}
