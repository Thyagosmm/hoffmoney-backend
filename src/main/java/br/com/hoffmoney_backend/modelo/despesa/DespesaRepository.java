package br.com.hoffmoney_backend.modelo.despesa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface DespesaRepository extends JpaRepository<Despesa, Long> {
     List<Despesa> findByUsuarioId(Long usuarioId);

     Optional<Despesa> findByIdAndUsuarioId(Long id, Long usuarioId);

     // Método para filtragem com base em múltiplos critérios
     @Query("SELECT d FROM Despesa d WHERE "
               + "(:dataDeCobranca IS NULL OR d.dataDeCobranca = :dataDeCobranca) AND "
               + "(:valor IS NULL OR d.valor = :valor) AND "
               + "(:categoria IS NULL OR d.categoriaDespesa.id = :categoria) AND "
               + "(:nome IS NULL OR d.nome LIKE %:nome%) AND "
               + "d.usuario.id = :usuarioId")
     List<Despesa> filtrarDespesas(@Param("dataDeCobranca") LocalDate dataDeCobranca,
               @Param("valor") Double valor,
               @Param("categoria") Long categoria,
               @Param("nome") String nome,
               @Param("usuarioId") Long usuarioId);
}
