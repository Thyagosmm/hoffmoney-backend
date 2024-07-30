package br.com.hoffmoney_backend.modelo.despesa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DespesaRepository extends JpaRepository<Despesa, Long> {
    List<Despesa> findByUsuarioIdAndNome(Long usuarioId, String nome);

    List<Despesa> findByUsuarioId(Long usuarioId);
}