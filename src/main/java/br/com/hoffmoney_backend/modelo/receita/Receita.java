package br.com.hoffmoney_backend.modelo.receita;

import java.time.LocalDate;

import br.com.hoffmoney_backend.modelo.categoriareceita.CategoriaReceita;
import br.com.hoffmoney_backend.modelo.usuario.Usuario;
import br.com.hoffmoney_backend.util.entity.EntidadeAuditavel;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "Receita")
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Receita extends EntidadeAuditavel {

    @ManyToOne
    private CategoriaReceita categoriaReceita;

    @ManyToOne
    private Usuario usuario;

    @Column(length = 100, nullable = false)
    private String nome;

    @Column(length = 255)
    private String descricao;

    @Column(nullable = false)
    private Double valor;

    @Column(nullable = false)
    private LocalDate dataDeCobranca;

    @Column(nullable = false)
    private Boolean paga;
}