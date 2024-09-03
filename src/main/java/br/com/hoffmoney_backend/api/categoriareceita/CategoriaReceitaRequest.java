package br.com.hoffmoney_backend.api.categoriareceita;

import br.com.hoffmoney_backend.modelo.categoriareceita.CategoriaReceita;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CategoriaReceitaRequest {

    @NotBlank(message = "A descrição é de preenchimento obrigatório")
    @Size(max = 100, message = "A descrição deve ter no máximo 100 caracteres")
    private String descricaoCategoriaReceita;

    public CategoriaReceita build() {
        
        return CategoriaReceita.builder()
                .descricaoCategoriaReceita(descricaoCategoriaReceita)
                .build();
    }
}
