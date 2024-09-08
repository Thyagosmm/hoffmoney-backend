package br.com.hoffmoney_backend.api.receita;

import java.time.LocalDate;

import org.hibernate.validator.constraints.Length;

import br.com.hoffmoney_backend.modelo.categoriareceita.CategoriaReceita;
import br.com.hoffmoney_backend.modelo.receita.Receita;
import br.com.hoffmoney_backend.modelo.usuario.Usuario;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReceitaRequest {

    private Long idCategoriaReceita;

    private Usuario usuario;

    @NotBlank(message = "O nome da receita precisa ser informado")
    private String nome;

    @NotNull(message = "O valor da receita precisa ser informado")
    private Double valor;

    @Length(max = 200, message = "A descrição deverá ter no máximo {max} caracteres")
    private String descricao;

    private LocalDate dataDeCobranca;

    private Boolean paga;

    public Receita build(CategoriaReceita categoria) {
        return Receita.builder()
                .usuario(usuario)
                .categoriaReceita(categoria)
                .nome(nome)
                .valor(valor)
                .descricao(descricao)
                .dataDeCobranca(dataDeCobranca)
                .paga(paga)
                .build();
    }
}