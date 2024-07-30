package br.com.hoffmoney_backend.api.despesa;

import java.util.Date;

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
    private Long usuarioId;
    private String nome;
    private Double valor;
    private LocalDate dataDeCobranca;
    private Boolean paga;
    private Boolean fixa;

    public Despesa build() {
        return Despesa.builder()
                .usuario(usuarioId)
                .nome(nome)
                .valor(valor)
                .dataDeCobranca(dataDeCobranca)
                .paga(paga)
                .fixa(fixa)
                .build();
    }
}