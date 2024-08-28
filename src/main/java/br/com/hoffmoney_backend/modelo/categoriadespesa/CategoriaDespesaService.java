package br.com.hoffmoney_backend.modelo.categoriadespesa;

import java.time.LocalDate;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import jakarta.transaction.Transactional;

@Service
public class CategoriaDespesaService {

  @Autowired
    private CategoriaDespesaRepository repository;

    @Transactional
    public CategoriaDespesa save(CategoriaDespesa categoriaDespesa) {
        categoriaDespesa.setHabilitado(Boolean.TRUE);
        categoriaDespesa.setVersao(1L);
        categoriaDespesa.setDataCriacao(LocalDate.now());
        return repository.save(categoriaDespesa);
    }

    public List<CategoriaDespesa> listarTodos() {

        return repository.findAll();
    }

    public CategoriaDespesa obterPorID(Long id) {

        return repository.findById(id).get();
    }

    @Transactional
    public void update(Long id, CategoriaDespesa categoriaDespesaAlterada) {

        CategoriaDespesa categoriaDespesa = repository.findById(id).get();
        categoriaDespesa.setDescricaoDespesa(categoriaDespesaAlterada.getDescricaoDespesa());
        categoriaDespesa.setVersao(categoriaDespesa.getVersao() + 1);
        repository.save(categoriaDespesa);
    }

    @Transactional
    public void delete(Long id) {

        CategoriaDespesa categoriaDespesa = repository.findById(id).get();
        categoriaDespesa.setHabilitado(Boolean.FALSE);
        categoriaDespesa.setVersao(categoriaDespesa.getVersao() + 1);

        repository.save(categoriaDespesa);
    }
}