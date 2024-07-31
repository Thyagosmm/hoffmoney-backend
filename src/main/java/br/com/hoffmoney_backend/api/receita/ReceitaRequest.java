package br.com.hoffmoney_backend.api.receita;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;

import br.com.hoffmoney_backend.modelo.receita.Receita;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor

public class ReceitaRequest {

    private String nome;

    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate dataRecebimento;

    private Double valor;

    private String categoria;
    
    // private boolean recorrente;

    private String descricao;

    public Receita build() {

        return Receita.builder()
                .nome(nome)
                .dataRecebimento(dataRecebimento)
                .valor(valor)
                .categoria(categoria)
                // .recorrente(recorrente)
                .descricao(descricao)
                .build();
    }

}
