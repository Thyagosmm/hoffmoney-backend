package br.com.hoffmoney_backend.modelo.despesa;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import jakarta.transaction.Transactional;

import java.util.Optional;

@Service
public class DespesaService {

    @Autowired
    private DespesaRepository despesaRepository;

    @Transactional
    public List<Despesa> listarDespesas() {
        return despesaRepository.findAll();
    }

    @Transactional
    public Optional<Despesa> findById(Long id) {
        return despesaRepository.findById(id);
    }

    @Transactional
    public Despesa save(Despesa despesa) {
        return despesaRepository.save(despesa);
    }

    @Transactional
    public void deleteById(Long id) {
        despesaRepository.deleteById(id);
    }

    public Despesa update(Long id, Despesa despesaDetails) {
        return despesaRepository.findById(id).map(despesa -> {
            despesa.setDescricao(despesaDetails.getDescricao());
            despesa.setValor(despesaDetails.getValor());
            despesa.setData(despesaDetails.getData());
            despesa.setCategoria(despesaDetails.getCategoria());
            despesa.setRecorrente(despesaDetails.isRecorrente());
            despesa.setUsuario(despesaDetails.getUsuario());
            return despesaRepository.save(despesa);
        }).orElseThrow(() -> new RuntimeException("Despesa not found with id " + id));
    }

    public void cadastrarDespesaRepetida(Despesa despesaOriginal, int quantidade, Periodo periodo) {
        List<Despesa> despesas = new ArrayList<>();
        Calendar calendar = Calendar.getInstance();

        for (int i = 0; i < quantidade; i++) {
            Despesa novaDespesa = new Despesa();
            novaDespesa.setUsuario(despesaOriginal.getUsuario());
            novaDespesa.setDescricao(despesaOriginal.getDescricao());
            novaDespesa.setValor(despesaOriginal.getValor());
            novaDespesa.setData(despesaOriginal.getData());

            despesas.add(novaDespesa);

            // Incrementa a data com base no período
            switch (periodo) {
                case daily:
                    calendar.add(Calendar.DAY_OF_MONTH, 1);
                    break;
                case weekly:
                    calendar.add(Calendar.WEEK_OF_YEAR, 1);
                    break;
                case monthly:
                    calendar.add(Calendar.MONTH, 1);
                    break;
                case yearly:
                    calendar.add(Calendar.YEAR, 1);
                    break;
            }
        }

        despesaRepository.saveAll(despesas);
    }

    public enum Periodo {
        daily, weekly, monthly, yearly
    }
}