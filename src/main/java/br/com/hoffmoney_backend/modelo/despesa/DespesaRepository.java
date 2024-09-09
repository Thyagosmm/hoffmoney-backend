package br.com.hoffmoney_backend.modelo.despesa;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface DespesaRepository extends JpaRepository<Despesa, Long>, JpaSpecificationExecutor<Despesa> {

        List<Despesa> findByUsuarioId(Long usuarioId);

        Optional<Despesa> findByIdAndUsuarioId(Long id, Long usuarioId);

}
