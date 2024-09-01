package br.com.hoffmoney_backend.modelo.receita;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.hoffmoney_backend.modelo.usuario.Usuario;
import br.com.hoffmoney_backend.modelo.usuario.UsuarioRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class ReceitaService {

    @Autowired
    private ReceitaRepository receitaRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Transactional
    public Receita salvarReceita(Receita receita) {
        Usuario usuario = usuarioRepository.findById(receita.getUsuario().getId())
            .orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado"));
        receita.setUsuario(usuario);
        receita.setHabilitado(Boolean.TRUE);
        receita.setVersao(1L);
        receita.setDataCriacao(LocalDate.now());
        
        Receita receitaSalva = receitaRepository.save(receita);
        
        if (receitaSalva.getPaga()) {
        usuarioRepository.decrementarSaldoPorIdUsuario(receita.getUsuario().getId(), receita.getValor());
        }
        
        return receitaSalva;
    }

    @Transactional
    public List<Receita> listarTodasReceitas() {
        return receitaRepository.findAll();
    }

    @Transactional
    public Optional<Receita> consultarReceitaPorId(Long id) {
        return receitaRepository.findById(id);
    }

    @Transactional
    public Optional<Receita> consultarReceitaPorIdEUsuarioId(Long id, Long usuarioId) {
        return receitaRepository.findByIdAndUsuarioId(id, usuarioId);
    }

    public enum Periodo {
        DIARIO, SEMANAL, MENSAL, ANUAL
    }

    @Transactional
    public void atualizarReceita(Long id, Long usuarioId, Receita novosDados) {
        Receita receita = receitaRepository.findByIdAndUsuarioId(id, usuarioId)
            .orElseThrow(() -> new EntityNotFoundException("Despesa não encontrada para o usuário especificado"));
        receita.setNome(novosDados.getNome());
        receita.setDescricao(novosDados.getDescricao());
        receita.setValor(novosDados.getValor());
        receita.setCategoriaReceita(novosDados.getCategoriaReceita());
        receita.setRecorrente(novosDados.getRecorrente());
        receita.setPeriodo(novosDados.getPeriodo());
        receita.setDataDeCobranca(novosDados.getDataDeCobranca());
        receita.setPaga(novosDados.getPaga());
        receita.setVersao(receita.getVersao() + 1);
        receita.setDataUltimaModificacao(LocalDate.now());
        receitaRepository.save(receita);
    }

    @Transactional
    public void deletarReceita(Long id, Long usuarioId) {
        Receita receita = receitaRepository.findByIdAndUsuarioId(id, usuarioId)
            .orElseThrow(() -> new EntityNotFoundException("Despesa não encontrada para o usuário especificado"));
        receitaRepository.delete(receita);
    }

    public List<Receita> listarReceitasPorUsuarioId(Long usuarioId) {
        return receitaRepository.findByUsuarioId(usuarioId);
    }

    public List<Receita> filtrar(LocalDate dataDeCobranca, Double valor, String categoria, String nome) {
        List<Receita> listaReceitas = receitaRepository.findAll();
        
        if (dataDeCobranca != null && valor == null && (categoria == null || "".equals(categoria)) && (nome == null || "".equals(nome))) {
            listaReceitas = receitaRepository.consultarPorDataDeCobranca(dataDeCobranca);
        } else if (dataDeCobranca == null && valor != null && (categoria == null || "".equals(categoria)) && (nome == null || "".equals(nome))) {
            listaReceitas = receitaRepository.consultarPorValor(valor);
        } else if (dataDeCobranca == null && valor == null && (categoria != null && !"".equals(categoria)) && (nome == null || "".equals(nome))) {
            listaReceitas = receitaRepository.consultarPorCategoria(categoria);
        } else if (dataDeCobranca == null && valor == null && (categoria == null || "".equals(categoria)) && (nome != null && !"".equals(nome))) {
            listaReceitas = receitaRepository.consultarPorNome(nome);
        }
        
        return listaReceitas;
    }
}
