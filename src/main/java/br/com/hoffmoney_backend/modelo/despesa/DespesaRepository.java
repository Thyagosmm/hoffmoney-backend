package br.com.hoffmoney_backend.modelo.despesa;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

import br.com.hoffmoney_backend.modelo.usuario.Usuario;

public interface DespesaRepository extends JpaRepository<Despesa, Long> {
    List<Despesa> findByUsuario(Usuario usuario);

}