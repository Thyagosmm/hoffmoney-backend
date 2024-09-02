package br.com.hoffmoney_backend.modelo.categoriareceita;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import br.com.hoffmoney_backend.modelo.receita.ReceitaRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

@Service
public class CategoriaReceitaService {

    @Autowired
    private CategoriaReceitaRepository categoriaReceitaRepository;

    @Autowired
    private ReceitaRepository  receitaRepository;

    @Transactional
    public CategoriaReceita save(CategoriaReceita categoriaReceita) {
        categoriaReceita.setHabilitado(Boolean.TRUE);
        categoriaReceita.setVersao(1L);
        categoriaReceita.setDataCriacao(LocalDate.now());
        return categoriaReceitaRepository.save(categoriaReceita);
    }

    public List<CategoriaReceita> listarTodos() {
        return categoriaReceitaRepository.findAll();
    }

    public CategoriaReceita obterPorID(Long id) {
        return categoriaReceitaRepository.findById(id).get();
    }

    public CategoriaReceita findById(Long id) {
        return categoriaReceitaRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Categoria de despesa não encontrada"));
    }

    @Transactional
    public void update(Long id, CategoriaReceita categoriaReceitaAlterada) {
        CategoriaReceita categoriaReceita = categoriaReceitaRepository.findById(id).get();
        categoriaReceita.setDescricaoCategoriaReceita(categoriaReceitaAlterada.getDescricaoCategoriaReceita());
        categoriaReceita.setVersao(categoriaReceita.getVersao() + 1);
        categoriaReceitaRepository.save(categoriaReceita);
    }

    @Transactional
    public void delete(Long id) {
        CategoriaReceita categoriaReceita = categoriaReceitaRepository.findById(id).get();
        categoriaReceita.setHabilitado(Boolean.FALSE);
        categoriaReceita.setVersao(categoriaReceita.getVersao() + 1);

        // Se necessário, também desabilite as Receitas relacionadas
        categoriaReceita.getReceitas().forEach(receita -> {
            receita.setHabilitado(Boolean.FALSE);
            receitaRepository.save(receita);
        });

        categoriaReceitaRepository.save(categoriaReceita);
    }

    public CategoriaReceita consultarCategoriaReceitaPorId(Long id) {
        Assert.notNull(id, "The given id must not be null");
        return categoriaReceitaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("CategoriaReceita not found with id: " + id));
    }
}
