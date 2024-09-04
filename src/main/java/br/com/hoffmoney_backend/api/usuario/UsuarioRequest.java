package br.com.hoffmoney_backend.api.usuario;

import org.hibernate.validator.constraints.Length;

import br.com.hoffmoney_backend.modelo.usuario.Usuario;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioRequest {

    @NotBlank(message = "O nome é de preenchimento obrigatótio")
    @Length(max = 100, message = "O nome deverá ter no máximo {max} caracteres")
    private String nome;

    @NotBlank(message = "O e-mail é de preenchimento obrigatótio.")
    @Email(message = "O e-mail precisa ser válido.")
    private String email;

    @NotBlank(message = "A senha é de preenchimento obrigatótio")
    @Length(min = 8, max = 24, message = "A senha deve ter entre {min} e {max} caracteres")
    private String senha;

    private Double saldo;
    private Double limite;

    public Usuario build() {
        return Usuario.builder()
                .nome(nome)
                .email(email)
                .senha(senha)
                .saldo(saldo)
                .limite(limite)

                .build();
    }
}
