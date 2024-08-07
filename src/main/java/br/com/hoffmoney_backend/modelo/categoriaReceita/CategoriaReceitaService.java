package br.com.hoffmoney_backend.modelo.categoriaReceita;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;

@Service
public class CategoriaReceitaService {

    @Autowired
    private CategoriaReceitaRepository repository;

    @Transactional
    public CategoriaReceita save(CategoriaReceita categoria) {

        categoria.setHabilitado(Boolean.TRUE);
        categoria.setVersao(1L);
        categoria.setDataCriacao(LocalDate.now());
        return repository.save(categoria);
    }

    public List<CategoriaReceita> listarTodos() {

        return repository.findAll();
    }

    public CategoriaReceita obterPorID(Long id) {

        return repository.findById(id).get();
    }

    @Transactional
    public void update(Long id, CategoriaReceita categoriaAlterado) {

        CategoriaReceita categoria = repository.findById(id).get();
        categoria.setDescricao(categoriaAlterado.getDescricao());

        categoria.setVersao(categoria.getVersao() + 1);
        repository.save(categoria);
    }

    @Transactional
    public void delete(Long id) {

        CategoriaReceita categoria = repository.findById(id).get();
        categoria.setHabilitado(Boolean.FALSE);
        categoria.setVersao(categoria.getVersao() + 1);

        repository.save(categoria);
    }


    
}
