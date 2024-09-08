package br.com.hoffmoney_backend.modelo.usuario;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    Optional<Usuario> findById(Usuario usuarioId);

    Usuario findByEmail(String email);

    boolean existsByEmail(String email);

    // Método para buscar o usuário pelo token de recuperação de senha
    Usuario findByResetToken(String resetToken);

    @Modifying
    @Query("UPDATE Usuario u SET u.saldo = u.saldo - :valor WHERE u.id = :usuarioId")
    void decrementarSaldoPorIdUsuario(@Param("usuarioId") Long usuarioId, @Param("valor") Double valor);
}