package br.com.hoffmoney_backend.modelo.despesa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DespesaRepository extends JpaRepository<Despesa, Long> {

    List<Despesa> findByUsuarioId(Long usuarioId);

    Optional<Despesa> findByIdAndUsuarioId(Long id, Long usuarioId);
}
