package br.com.hoffmoney_backend.api.receita;

import java.time.LocalDate;

import br.com.hoffmoney_backend.modelo.categoriareceita.CategoriaReceita;
import br.com.hoffmoney_backend.modelo.receita.Receita;
import br.com.hoffmoney_backend.modelo.usuario.Usuario;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReceitaRequest {

    private Long idCategoriaReceita;
    private Usuario usuario;
    private String nome;
    private Double valor;
    private String descricao;
    private LocalDate dataDeCobranca;
    private Boolean paga;

    public Receita build(CategoriaReceita categoria) {
        return Receita.builder()
                .usuario(usuario)
                .categoriaReceita(categoria)
                .nome(nome)
                .valor(valor)
                .dataDeCobranca(dataDeCobranca)
                .paga(paga)
                .build();
    }
}