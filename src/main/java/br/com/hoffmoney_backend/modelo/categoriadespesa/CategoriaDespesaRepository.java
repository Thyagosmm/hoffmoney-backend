package br.com.hoffmoney_backend.modelo.categoriadespesa;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.hoffmoney_backend.modelo.usuario.Usuario;

@Repository
public interface CategoriaDespesaRepository extends JpaRepository<CategoriaDespesa, Long> {

    Optional<Usuario> findById(CategoriaDespesa categoriaDespesa);
}
