package br.com.hoffmoney_backend.modelo.despesa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.hoffmoney_backend.modelo.usuario.Usuario;

import java.util.List;

@Repository
public interface DespesaRepository extends JpaRepository<Despesa, Long> {

    List<Despesa> findByUsuarioIdAndNome(Usuario usuario, String nome);

    List<Despesa> findByUsuarioId(Long usuarioId);
}