package br.com.hoffmoney_backend.modelo.despesa;

import java.time.LocalDate;
// import java.time.LocalDate;
// import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import br.com.hoffmoney_backend.modelo.categoriadespesa.CategoriaDespesa;
import br.com.hoffmoney_backend.modelo.categoriadespesa.CategoriaDespesaRepository;
import br.com.hoffmoney_backend.modelo.usuario.Usuario;
import br.com.hoffmoney_backend.modelo.usuario.UsuarioRepository;
import jakarta.persistence.EntityNotFoundException;
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
        Despesa despesaExistente = despesaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Despesa não encontrada"));

        double valorAnterior = despesaExistente.getValor();

        despesaExistente.setCategoriaDespesa(despesaAtualizada.getCategoriaDespesa());
        despesaExistente.setNome(despesaAtualizada.getNome());
        despesaExistente.setDescricao(despesaAtualizada.getDescricao());
        despesaExistente.setValor(despesaAtualizada.getValor());
        despesaExistente.setDataDeCobranca(despesaAtualizada.getDataDeCobranca());
        despesaExistente.setPaga(despesaAtualizada.isPaga());
        despesaExistente.setVersao(despesaExistente.getVersao() + 1);
        despesaRepository.save(despesaExistente);

        if (despesaExistente.isPaga()) {
            Usuario usuario = despesaExistente.getUsuario();
            usuario.setSaldo(usuario.getSaldo() + valorAnterior - despesaExistente.getValor());
            usuarioRepository.save(usuario);
        }
    }

    @Transactional
    public void deletarDespesa(Long id, Long usuarioId) {
        Despesa despesa = despesaRepository.findByIdAndUsuarioId(id, usuarioId)
                .orElseThrow(() -> new RuntimeException("Despesa não encontrada"));

        despesaRepository.deleteById(id);

        if (despesa.isPaga()) {
            Usuario usuario = usuarioRepository.findById(usuarioId)
                    .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
            usuario.setSaldo(usuario.getSaldo() + despesa.getValor());
            usuarioRepository.save(usuario);
        }
    }

    @Transactional
    public void atualizarPaga(Long despesaId, Boolean novaSituacaoPaga) {
        Despesa despesa = despesaRepository.findById(despesaId)
                .orElseThrow(() -> new EntityNotFoundException("Despesa não encontrada para o ID especificado"));

        Usuario usuario = despesa.getUsuario();

        if (novaSituacaoPaga && !despesa.getPaga()) {
            usuario.setSaldo(usuario.getSaldo() - despesa.getValor());
        } else if (!novaSituacaoPaga && despesa.getPaga()) {
            usuario.setSaldo(usuario.getSaldo() + despesa.getValor());
        }

        despesa.setPaga(novaSituacaoPaga);
        despesaRepository.save(despesa);
        usuarioRepository.save(usuario);
    }

    public List<Despesa> filtrarDespesas(String nome, Double valor, LocalDate dataDeCobranca, Long idCategoriaDespesa) {
        Specification<Despesa> spec = Specification.where(DespesaSpecifications.hasNome(nome))
                .and(DespesaSpecifications.hasValor(valor))
                .and(DespesaSpecifications.hasDataDeCobranca(dataDeCobranca))
                .and(DespesaSpecifications.hasCategoriaDespesa(idCategoriaDespesa));
        return despesaRepository.findAll(spec);
    }
}
