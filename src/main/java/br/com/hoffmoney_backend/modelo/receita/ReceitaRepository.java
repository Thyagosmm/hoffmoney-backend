package br.com.hoffmoney_backend.modelo.receita;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ReceitaRepository extends JpaRepository<Receita, Long> {

    List<Receita> findByUsuarioId(Long usuarioId);

    Optional<Receita> findByIdAndUsuarioId(Long id, Long usuarioId);
}
