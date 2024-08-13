package br.com.hoffmoney_backend.modelo.receita;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface ReceitaRepository extends JpaRepository<Receita, Long> {

    List<Receita> findByUsuarioId(Long usuarioId);

    Optional<Receita> findByIdAndUsuarioId(Long id, Long usuarioId);

    @Query(value = "SELECT r FROM Receita r WHERE r.dataDeCobranca = :dataDeCobranca")
    List<Receita> consultarPorDataDeCobranca (LocalDate dataDeCobranca);

    @Query(value = "SELECT r FROM Receita r WHERE r.valor = :valor")
    List<Receita> consultarPorValor (Double valor);

    @Query(value = "SELECT r FROM Receita r WHERE r.categoria like :categoria% ORDER BY r.categoria")
    List<Receita> consultarPorCategoria (String categoria);

    @Query(value = "SELECT r FROM Receita r WHERE r.nome like :nome% ORDER BY r.nome")
    List<Receita> consultarPorNome(String nome);

}
