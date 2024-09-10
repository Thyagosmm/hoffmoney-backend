package br.com.hoffmoney_backend.modelo.despesa;

import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;

public class DespesaSpecifications {

  public static Specification<Despesa> hasNome(String nome) {
    return (root, query, criteriaBuilder) -> {
      if (nome == null || nome.isEmpty()) {
        return criteriaBuilder.conjunction();
      }
      return criteriaBuilder.like(root.get("nome"), "%" + nome + "%");
    };
  }

  public static Specification<Despesa> hasValor(Double valor) {
    return (root, query, criteriaBuilder) -> {
      if (valor == null) {
        return criteriaBuilder.conjunction();
      }
      return criteriaBuilder.equal(root.get("valor"), valor);
    };
  }

  public static Specification<Despesa> hasDataDeCobranca(LocalDate dataDeCobranca) {
    return (root, query, criteriaBuilder) -> {
      if (dataDeCobranca == null) {
        return criteriaBuilder.conjunction();
      }
      return criteriaBuilder.equal(root.get("dataDeCobranca"), dataDeCobranca);
    };
  }

  public static Specification<Despesa> hasCategoriaDespesa(Long idCategoriaDespesa) {
    return (root, query, criteriaBuilder) -> {
      if (idCategoriaDespesa == null) {
        return criteriaBuilder.conjunction();
      }
      return criteriaBuilder.equal(root.get("categoriaDespesa").get("id"), idCategoriaDespesa);
    };
  }
}
