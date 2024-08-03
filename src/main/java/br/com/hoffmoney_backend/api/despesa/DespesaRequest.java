package br.com.hoffmoney_backend.api.despesa;

import java.time.LocalDate;

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
    private Usuario usuario;
    private String nome;
    private Double valor;
    private String categoria;
    private Boolean recorrente;
    private String periodo;
    private LocalDate dataDeCobranca;
    private Boolean paga;

    public Despesa build() {
        return Despesa.builder()
                .usuario(usuario)
                .nome(nome)
                .valor(valor)
                .categoria(categoria)
                .recorrente(recorrente)
                .periodo(periodo)
                .dataDeCobranca(dataDeCobranca)
                .paga(paga)
                .build();
    }
}