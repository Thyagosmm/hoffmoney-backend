package br.com.hoffmoney_backend.modelo.receita;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ReceitaRepository extends JpaRepository<Receita, Long>, JpaSpecificationExecutor<Receita> {

        List<Receita> findByUsuarioId(Long usuarioId);

        Optional<Receita> findByIdAndUsuarioId(Long id, Long usuarioId);

}