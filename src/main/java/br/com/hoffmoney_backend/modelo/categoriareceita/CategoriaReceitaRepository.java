package br.com.hoffmoney_backend.modelo.categoriareceita;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.hoffmoney_backend.modelo.usuario.Usuario;

@Repository
public interface CategoriaReceitaRepository extends JpaRepository<CategoriaReceita, Long> {

    Optional<Usuario> findById(CategoriaReceita categoriaReceita);
}
