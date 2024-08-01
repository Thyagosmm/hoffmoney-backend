package br.com.hoffmoney_backend.modelo.despesa;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.hoffmoney_backend.modelo.usuario.Usuario;
import br.com.hoffmoney_backend.modelo.usuario.UsuarioRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

import java.util.List;
import java.util.Optional;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;

@Service
public class DespesaService {

    @Autowired
    private DespesaRepository despesaRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Transactional
    public List<Despesa> listarTodasDespesas() {
        return despesaRepository.findAll();
    }

    @Transactional
    public Optional<Despesa> consultarDespesaPorId(Long id) {
        return despesaRepository.findById(id);
    }

    @Transactional
    public Despesa salvarDespesa(Despesa despesa) {
        Usuario usuario = usuarioRepository.findById(despesa.getUsuario().getId())
        .orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado"));
        despesa.setUsuario(usuario); 
        despesa.setHabilitado(Boolean.TRUE);
        despesa.setVersao(1L);
        despesa.setDataCriacao(LocalDate.now());
        return despesaRepository.save(despesa);
    }

    @Transactional
    public void deletarDespesa(Long id) {
        despesaRepository.deleteById(id);
    }

    public enum Periodo {
        diario, semanal, mensal, anual
    }

    @Transactional
    public List<Despesa> cadastrarDespesaRepetida(Despesa despesa, Periodo periodo) {
        List<Despesa> despesas = new ArrayList<Despesa>();

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(java.sql.Date.valueOf(despesa.getDataDeCobranca()));

        Usuario usuario = usuarioRepository.findById(despesa.getUsuario().getId())
        .orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado"));

        int quantidade = despesa.getVezes();
        for (int i = 0; i < quantidade; i++) {
            Despesa novaDespesa = new Despesa();
            novaDespesa.setUsuario(usuario);
            novaDespesa.setNome(despesa.getNome());
            novaDespesa.setDescricao(despesa.getDescricao());
            novaDespesa.setValor(despesa.getValor());
            novaDespesa.setCategoria(despesa.getCategoria());
            novaDespesa.setRecorrente(despesa.getRecorrente());
            novaDespesa.setVezes(despesa.getVezes());
            novaDespesa.setPeriodo(despesa.getPeriodo());
            novaDespesa.setDataDeCobranca(despesa.getDataDeCobranca());
            novaDespesa.setPaga(despesa.getPaga());
            novaDespesa.setHabilitado(Boolean.TRUE);
            novaDespesa.setVersao(1L);
            novaDespesa.setDataCriacao(LocalDate.now());
            despesas.add(novaDespesa);

            // Incrementa a data com base no período
            switch (periodo) {
                case diario:
                    calendar.add(Calendar.DAY_OF_MONTH, 1);
                    break;
                case semanal:
                    calendar.add(Calendar.WEEK_OF_YEAR, 1);
                    break;
                case mensal:
                    calendar.add(Calendar.MONTH, 1);
                    break;
                case anual:
                    calendar.add(Calendar.YEAR, 1);
                    break;
            }
            novaDespesa.setDataDeCobranca(
                    calendar.getTime().toInstant().atZone(calendar.getTimeZone().toZoneId()).toLocalDate());
        }

        return despesaRepository.saveAll(despesas);
    }

    @Transactional
    public void atualizarDespesasRecorrentes(Despesa despesaOriginal, Despesa novosDados) {
        List<Despesa> despesas = despesaRepository.findByUsuarioIdAndNome(despesaOriginal.getUsuario(),
                despesaOriginal.getNome());
        for (Despesa despesa : despesas) {
            despesa.setNome(novosDados.getNome());
            despesa.setDescricao(novosDados.getDescricao());
            despesa.setValor(novosDados.getValor());
            despesa.setCategoria(novosDados.getCategoria());
            despesa.setRecorrente(novosDados.getRecorrente());
            despesa.setVezes(novosDados.getVezes());
            despesa.setPeriodo(novosDados.getPeriodo());
            despesa.setDataDeCobranca(novosDados.getDataDeCobranca());
            despesa.setPaga(novosDados.getPaga());
            despesa.setVersao(despesa.getVersao() + 1);
            despesa.setDataUltimaModificacao(LocalDate.now());

        }
        despesaRepository.saveAll(despesas);
    }

    @Transactional
    public void deletarDespesasRecorrentes(Despesa despesaOriginal) {
        List<Despesa> despesas = despesaRepository.findByUsuarioIdAndNome(despesaOriginal.getUsuario(),
                despesaOriginal.getNome());
        despesaRepository.deleteAll(despesas);
    }

    public List<Despesa> listarDespesasPorUsuarioId(Long usuarioId) {
        return despesaRepository.findByUsuarioId(usuarioId);
    }
}