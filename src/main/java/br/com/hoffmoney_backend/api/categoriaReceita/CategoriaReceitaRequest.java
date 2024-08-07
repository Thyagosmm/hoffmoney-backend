package br.com.hoffmoney_backend.api.categoriaReceita;

import br.com.hoffmoney_backend.modelo.categoriaReceita.CategoriaReceita;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor

public class CategoriaReceitaRequest {

    private String descricao;

    public CategoriaReceita build() {

        return CategoriaReceita.builder()
            .descricao(descricao)
            .build();
    }
    
}
