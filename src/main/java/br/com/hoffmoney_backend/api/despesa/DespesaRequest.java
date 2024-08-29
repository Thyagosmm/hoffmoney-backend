package br.com.hoffmoney_backend.api.despesa;

import java.time.LocalDate;
import br.com.hoffmoney_backend.modelo.categoriadespesa.CategoriaDespesa;
import br.com.hoffmoney_backend.modelo.despesa.Despesa;
import br.com.hoffmoney_backend.modelo.usuario.Usuario;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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

    public Despesa build() {
        CategoriaDespesa categoriaDespesa = null;
        if (idCategoriaDespesa != null) {
            categoriaDespesa = new CategoriaDespesa();
            categoriaDespesa.setId(idCategoriaDespesa);
        }

        return Despesa.builder()
                .usuario(usuario)
                .nome(nome)
                .valor(valor)
                .descricao(descricao)
                .dataDeCobranca(dataDeCobranca)
                .paga(paga)
                .categoriaDespesa(categoriaDespesa)
                .build();
    }
}