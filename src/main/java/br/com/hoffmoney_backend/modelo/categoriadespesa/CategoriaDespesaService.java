package br.com.hoffmoney_backend.modelo.categoriadespesa;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import br.com.hoffmoney_backend.modelo.despesa.DespesaRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

@Service
public class CategoriaDespesaService {

    @Autowired
    private CategoriaDespesaRepository categoriaDespesaRepository;

    @Autowired
    private DespesaRepository despesaRepository;

    @Transactional
    public CategoriaDespesa save(CategoriaDespesa categoriaDespesa) {
        categoriaDespesa.setHabilitado(Boolean.TRUE);
        categoriaDespesa.setVersao(1L);
        categoriaDespesa.setDataCriacao(LocalDate.now());
        return categoriaDespesaRepository.save(categoriaDespesa);
    }

    public List<CategoriaDespesa> listarTodos() {
        return categoriaDespesaRepository.findAll();
    }

    public CategoriaDespesa obterPorID(Long id) {
        return categoriaDespesaRepository.findById(id).get();
    }

    public CategoriaDespesa findById(Long id) {
        return categoriaDespesaRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Categoria de despesa não encontrada"));
    }

    @Transactional
    public void update(Long id, CategoriaDespesa categoriaDespesaAlterada) {
        CategoriaDespesa categoriaDespesa = categoriaDespesaRepository.findById(id).get();
        categoriaDespesa.setDescricaoCategoriaDespesa(categoriaDespesaAlterada.getDescricaoCategoriaDespesa());
        categoriaDespesa.setVersao(categoriaDespesa.getVersao() + 1);
        categoriaDespesaRepository.save(categoriaDespesa);
    }

    @Transactional
    public void delete(Long id) {
        CategoriaDespesa categoriaDespesa = categoriaDespesaRepository.findById(id).get();
        categoriaDespesa.setHabilitado(Boolean.FALSE);
        categoriaDespesa.setVersao(categoriaDespesa.getVersao() + 1);

        // Se necessário, também desabilite as despesas relacionadas
        categoriaDespesa.getDespesas().forEach(despesa -> {
            despesa.setHabilitado(Boolean.FALSE);
            despesaRepository.save(despesa);
        });

        categoriaDespesaRepository.save(categoriaDespesa);
    }

    public CategoriaDespesa consultarCategoriaDespesaPorId(Long id) {
        Assert.notNull(id, "The given id must not be null");
        return categoriaDespesaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("CategoriaDespesa not found with id: " + id));
    }
}
