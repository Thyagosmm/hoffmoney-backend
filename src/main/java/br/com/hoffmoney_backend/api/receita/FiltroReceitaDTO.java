package br.com.hoffmoney_backend.api.receita;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;
import org.springframework.format.annotation.DateTimeFormat;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FiltroReceitaDTO {
  private String nome;
  private Double valor;
  @DateTimeFormat(pattern = "yyyy-MM-dd")
  private LocalDate dataDeCobranca;
  private Long idCategoriaReceita;
}
