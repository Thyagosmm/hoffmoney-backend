package br.com.hoffmoney_backend.api.despesa;

import br.com.hoffmoney_backend.modelo.categoriadespesa.CategoriaDespesa;
import br.com.hoffmoney_backend.modelo.despesa.Despesa;
import br.com.hoffmoney_backend.modelo.usuario.Usuario;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

import org.hibernate.validator.constraints.Length;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DespesaRequest {

    private Long idCategoriaDespesa;

    private Usuario usuario;

    @NotBlank(message = "O nome da despesa precisa ser informado")
    private String nome;

    @NotNull(message = "O valor da despesa precisa ser informado")
    private Double valor;

    @Length(max = 200, message = "A descrição máxima é de {max} caracteres")
    private String descricao;

    private LocalDate dataDeCobranca;

    private Boolean paga;

    public Despesa build(CategoriaDespesa categoria) {
        return Despesa.builder()
                .usuario(usuario)
                .categoriaDespesa(categoria)
                .nome(nome)
                .valor(valor)
                .descricao(descricao)
                .dataDeCobranca(dataDeCobranca)
                .paga(paga)
                .build();
    }
}
