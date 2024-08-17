package br.com.hoffmoney_backend.modelo.despesa;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.hoffmoney_backend.modelo.usuario.Usuario;
import br.com.hoffmoney_backend.modelo.usuario.UsuarioRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

@Service
public class DespesaService {

    @Autowired
    private DespesaRepository despesaRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Transactional
    public Despesa salvarDespesa(Despesa despesa) {
        Usuario usuario = usuarioRepository.findById(despesa.getUsuario().getId())
            .orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado"));
        despesa.setUsuario(usuario);
        despesa.setHabilitado(Boolean.TRUE);
        despesa.setVersao(1L);
        despesa.setDataCriacao(LocalDate.now());
        
        Despesa despesaSalva = despesaRepository.save(despesa);
        
        if (despesaSalva.getPaga()) {
        usuarioRepository.decrementarSaldoPorIdUsuario(despesa.getUsuario().getId(), despesa.getValor());
        }
        
        return despesaSalva;
    }

    @Transactional
    public List<Despesa> listarTodasDespesas() {
        return despesaRepository.findAll();
    }

    @Transactional
    public Optional<Despesa> consultarDespesaPorId(Long id) {
        return despesaRepository.findById(id);
    }

    @Transactional
    public Optional<Despesa> consultarDespesaPorIdEUsuarioId(Long id, Long usuarioId) {
        return despesaRepository.findByIdAndUsuarioId(id, usuarioId);
    }

    public enum Periodo {
        DIARIO, SEMANAL, MENSAL, ANUAL
    }

    @Transactional
    public void atualizarDespesa(Long id, Long usuarioId, Despesa novosDados) {
        Despesa despesa = despesaRepository.findByIdAndUsuarioId(id, usuarioId)
            .orElseThrow(() -> new EntityNotFoundException("Despesa não encontrada para o usuário especificado"));
        despesa.setNome(novosDados.getNome());
        despesa.setDescricao(novosDados.getDescricao());
        despesa.setValor(novosDados.getValor());
        despesa.setCategoria(novosDados.getCategoria());
        despesa.setRecorrente(novosDados.getRecorrente());
        despesa.setPeriodo(novosDados.getPeriodo());
        despesa.setDataDeCobranca(novosDados.getDataDeCobranca());
        despesa.setPaga(novosDados.getPaga());
        despesa.setVersao(despesa.getVersao() + 1);
        despesa.setDataUltimaModificacao(LocalDate.now());
        despesaRepository.save(despesa);
    }

    @Transactional
    public void deletarDespesa(Long id, Long usuarioId) {
        Despesa despesa = despesaRepository.findByIdAndUsuarioId(id, usuarioId)
            .orElseThrow(() -> new EntityNotFoundException("Despesa não encontrada para o usuário especificado"));
        despesaRepository.delete(despesa);
    }

    public List<Despesa> listarDespesasPorUsuarioId(Long usuarioId) {
        return despesaRepository.findByUsuarioId(usuarioId);
    }

    @Transactional
    public void atualizarPaga(Long id, Boolean novaSituacaoPaga) {
        Despesa despesa = despesaRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Despesa não encontrada"));
        despesa.setPaga(novaSituacaoPaga);
        despesaRepository.save(despesa);
    }
}
