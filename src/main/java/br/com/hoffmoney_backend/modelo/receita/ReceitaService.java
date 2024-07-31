package br.com.hoffmoney_backend.modelo.receita;

import java.time.LocalDate;
// import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;

@Service
public class ReceitaService {

    @Autowired
    private ReceitaRepository repository;

    @Transactional
    public Receita save(Receita receita) {

        receita.setHabilitado(Boolean.TRUE);
        receita.setVersao(1L);
        receita.setDataCriacao(LocalDate.now());
        return repository.save(receita);
    }

    public List<Receita> listarTodos() {

        return repository.findAll();
    }

    public Receita obterPorID(Long id) {

        return repository.findById(id).get();
    }

    @Transactional
    public void update(Long id, Receita receitaAlterada) {

        Receita receita = repository.findById(id).get();
        receita.setNome(receitaAlterada.getNome());
        receita.setValor(receitaAlterada.getValor());
        receita.setCategoria(receitaAlterada.getCategoria());
        receita.setDataRecebimento(receitaAlterada.getDataRecebimento());
        receita.setDescricao(receitaAlterada.getDescricao());
        // receita.setRecorrente(receitaAlterada.isRecorrente());

        receita.setVersao(receita.getVersao() + 1);
        repository.save(receita);
    }

    @Transactional
    public void delete(Long id) {

        Receita receita = repository.findById(id).get();
        receita.setHabilitado(Boolean.FALSE);
        receita.setVersao(receita.getVersao() + 1);

        repository.save(receita);

    }

}
