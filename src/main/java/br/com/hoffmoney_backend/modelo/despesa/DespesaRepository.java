package br.com.hoffmoney_backend.modelo.despesa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface DespesaRepository extends JpaRepository<Despesa, Long> {

    List<Despesa> findByUsuarioId(Long usuarioId);

    Optional<Despesa> findByIdAndUsuarioId(Long id, Long usuarioId);

    @Query(value = "SELECT d FROM Despesa d WHERE d.dataDeCobranca = :dataDeCobranca")
    List<Despesa> consultarPorDataDeCobranca (LocalDate dataDeCobranca);

    @Query(value = "SELECT d FROM Despesa d WHERE d.valor = :valor")
    List<Despesa> consultarPorValor (Double valor);

    @Query(value = "SELECT d FROM Despesa d WHERE d.categoria like :categoria% ORDER BY d.categoria")
    List<Despesa> consultarPorCategoria (String categoria);

    @Query(value = "SELECT d FROM Despesa d WHERE d.nome like :nome% ORDER BY d.nome")
    List<Despesa> consultarPorNome(String nome);
}