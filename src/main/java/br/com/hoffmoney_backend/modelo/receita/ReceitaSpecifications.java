package br.com.hoffmoney_backend.modelo.receita;

import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;


public class ReceitaSpecifications {
  
  public static Specification<Receita> hasNome(String nome) {
    return (root, query, criteriaBuilder) -> {
      if (nome == null || nome.isEmpty()) {
        return criteriaBuilder.conjunction();
      }
      return criteriaBuilder.like(root.get("nome"), "%" + nome + "%");
    };
  }

  public static Specification<Receita> hasValor(Double valor) {
    return (root, query, criteriaBuilder) -> {
      if (valor == null) {
        return criteriaBuilder.conjunction();
      }
      return criteriaBuilder.equal(root.get("valor"), valor);
    };
  }

  public static Specification<Receita> hasDataDeCobranca(LocalDate dataDeCobranca) {
    return (root, query, criteriaBuilder) -> {
      if (dataDeCobranca == null) {
        return criteriaBuilder.conjunction();
      }
      return criteriaBuilder.equal(root.get("dataDeCobranca"), dataDeCobranca);
    };
  }

  public static Specification<Receita> hasCategoriaReceita(Long idCategoriaReceita) {
    return (root, query, criteriaBuilder) -> {
      if (idCategoriaReceita == null) {
        return criteriaBuilder.conjunction();
      }
      return criteriaBuilder.equal(root.get("categoriaReceita").get("id"), idCategoriaReceita);
    };
  }
}
