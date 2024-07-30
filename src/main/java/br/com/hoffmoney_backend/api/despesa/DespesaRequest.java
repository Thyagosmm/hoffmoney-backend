package br.com.hoffmoney_backend.api.despesa;

import java.time.LocalDate;

import br.com.hoffmoney_backend.modelo.despesa.Despesa;
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
    private String categoria;
    private Boolean recorrente;
    private Integer vezes;
    private String periodo;
    private LocalDate dataDeCobranca;
    private Boolean paga;

    public Despesa build() {
        return Despesa.builder()
                .usuarioId(usuarioId)
                .nome(nome)
                .valor(valor)
                .categoria(categoria)
                .recorrente(recorrente)
                .vezes(vezes)
                .periodo(periodo)
                .dataDeCobranca(dataDeCobranca)
                .paga(paga)
                .build();
    }
}