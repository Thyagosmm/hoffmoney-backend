package br.com.hoffmoney_backend.modelo.receita;

import br.com.hoffmoney_backend.modelo.categoriareceita.CategoriaReceita;
import br.com.hoffmoney_backend.modelo.usuario.Usuario;
import br.com.hoffmoney_backend.util.entity.EntidadeAuditavel;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.SQLRestriction;

import java.time.LocalDate;

@Entity
@Table(name = "Receita")
@SQLRestriction("habilitado = true")
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