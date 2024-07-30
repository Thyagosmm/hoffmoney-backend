package br.com.hoffmoney_backend.modelo.despesa;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.ArrayList;
import java.util.Calendar;

@Service
public class DespesaService {

    @Autowired
    private DespesaRepository despesaRepository;

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
    public void cadastrarDespesaRepetida(Despesa despesa, int quantidade, Periodo periodo) {
        List<Despesa> despesas = new ArrayList<Despesa>();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(java.sql.Date.valueOf(despesa.getDataDeCobranca()));

        for (int i = 0; i < quantidade; i++) {
            Despesa novaDespesa = new Despesa();
            novaDespesa.setUsuarioId(despesa.getUsuarioId());
            novaDespesa.setNome(despesa.getNome());
            novaDespesa.setDescricao(despesa.getDescricao());
            novaDespesa.setValor(despesa.getValor());
            novaDespesa.setCategoria(despesa.getCategoria());
            novaDespesa.setRecorrente(despesa.getRecorrente());
            novaDespesa.setVezes(despesa.getVezes());
            novaDespesa.setPeriodo(despesa.getPeriodo());
            novaDespesa.setDataDeCobranca(despesa.getDataDeCobranca());
            novaDespesa.setPaga(despesa.getPaga());

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

        despesaRepository.saveAll(despesas);
    }

    @Transactional
    public void atualizarDespesasRecorrentes(Despesa despesaOriginal, Despesa novosDados) {
        List<Despesa> despesas = despesaRepository.findByUsuarioIdAndNome(despesaOriginal.getUsuarioId(),
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
        }
        despesaRepository.saveAll(despesas);
    }

    @Transactional
    public void deletarDespesasRecorrentes(Despesa despesaOriginal) {
        List<Despesa> despesas = despesaRepository.findByUsuarioIdAndNome(despesaOriginal.getUsuarioId(),
                despesaOriginal.getNome());
        despesaRepository.deleteAll(despesas);
    }

    @Transactional
    public List<Despesa> listarDespesasPorUsuarioId(Long usuarioId) {
        return despesaRepository.findByUsuarioId(usuarioId);
    }

}