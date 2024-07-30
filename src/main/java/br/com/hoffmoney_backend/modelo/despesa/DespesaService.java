package br.com.hoffmoney_backend.modelo.despesa;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.ArrayList;
import java.util.Calendar;

@Service
public class DespesaService {

    @Autowired
    private DespesaRepository despesaRepository;

    public List<Despesa> listarTodasDespesas() {
        List<Despesa> despesas = despesaRepository.findAll();
        System.out.println("Despesas no serviço: " + despesas); // Log para verificar o retorno do repositório
        return despesas;
    }

    public Despesa salvarDespesa(Despesa despesa) {
        return despesaRepository.save(despesa);
    }

    public void deletarDespesa(Long id) {
        despesaRepository.deleteById(id);
    }

    public Despesa update(Long id, Despesa despesaDetails) {
        return despesaRepository.findById(id).map(despesa -> {
            despesa.setDescricao(despesaDetails.getDescricao());
            despesa.setValor(despesaDetails.getValor());
            despesa.setDataDeCobranca(despesaDetails.getDataDeCobranca());
            despesa.setCategoria(despesaDetails.getCategoria());
            despesa.setRecorrente(despesaDetails.getRecorrente());
            despesa.setUsuarioId(despesaDetails.getUsuarioId());
            return despesaRepository.save(despesa);
        }).orElseThrow(() -> new RuntimeException("Despesa not found with id " + id));
    }

    public enum Periodo {
        diario, semanal, mensal, anual
    }

    public void cadastrarDespesaRepetida(Despesa despesa, int quantidade, Periodo periodo) {
        List<Despesa> despesas = new ArrayList<>();
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

    public void deletarDespesasRecorrentes(Despesa despesaOriginal) {
        List<Despesa> despesas = despesaRepository.findByUsuarioIdAndNome(despesaOriginal.getUsuarioId(),
                despesaOriginal.getNome());
        despesaRepository.deleteAll(despesas);
    }

    public List<Despesa> listarDespesasPorUsuarioId(Long usuarioId) {
        return despesaRepository.findByUsuarioId(usuarioId);
    }

}