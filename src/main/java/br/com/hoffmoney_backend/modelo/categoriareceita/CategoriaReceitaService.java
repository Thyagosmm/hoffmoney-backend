package br.com.hoffmoney_backend.modelo.categoriareceita;

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
  public CategoriaReceita save(CategoriaReceita categoriaReceita) {
    categoriaReceita.setHabilitado(Boolean.TRUE);
    categoriaReceita.setVersao(1L);
    categoriaReceita.setDataCriacao(LocalDate.now());
    return repository.save(categoriaReceita);
  }

  public List<CategoriaReceita> listarTodos() {
    return repository.findAll();
  }

  public CategoriaReceita obterPorID(Long id) {
    return repository.findById(id).orElseThrow(() -> new RuntimeException("CategoriaReceita não encontrada com id: " + id));
  }

  @Transactional
  public void update(Long id, CategoriaReceita categoriaReceitaAlterada) {
    CategoriaReceita categoriaReceita = repository.findById(id).orElseThrow(() -> new RuntimeException("CategoriaReceita não encontrada com id: " + id));
    categoriaReceita.setDescricaoCategoriaReceita(categoriaReceitaAlterada.getDescricaoCategoriaReceita());
    categoriaReceita.setVersao(categoriaReceita.getVersao() + 1);
    repository.save(categoriaReceita);
  }

  @Transactional
  public void delete(Long id) {
    CategoriaReceita categoriaReceita = repository.findById(id).orElseThrow(() -> new RuntimeException("CategoriaReceita não encontrada com id: " + id));
    categoriaReceita.setHabilitado(Boolean.FALSE);
    categoriaReceita.setVersao(categoriaReceita.getVersao() + 1);
    repository.save(categoriaReceita);
  }
}
