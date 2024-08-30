package br.com.hoffmoney_backend.api.categoriadespesa;

import br.com.hoffmoney_backend.modelo.categoriadespesa.CategoriaDespesa;
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

public class CategoriaDespesaRequest {

  @NotBlank(message = "A descrição é de preenchimento obrigatório")
  @Size(max = 100, message = "A descrição deve ter no máximo 100 caracteres")
  private String descricaoCategoriaDespesa;

  public CategoriaDespesa build() {

      return CategoriaDespesa.builder()
              .descricaoCategoriaDespesa(descricaoCategoriaDespesa)
              .build();
  }
  
}    
