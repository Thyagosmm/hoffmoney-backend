package br.com.hoffmoney_backend.api.despesa;

import br.com.hoffmoney_backend.modelo.categoriadespesa.CategoriaDespesa;
import br.com.hoffmoney_backend.modelo.despesa.Despesa;
import br.com.hoffmoney_backend.modelo.usuario.Usuario;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DespesaRequest {

    private Long idCategoriaDespesa;
    private Usuario usuario;
    private String nome;
    private Double valor;
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
