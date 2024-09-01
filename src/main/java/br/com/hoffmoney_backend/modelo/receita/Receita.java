package br.com.hoffmoney_backend.modelo.receita;

import br.com.hoffmoney_backend.modelo.categoriareceita.CategoriaReceita;
import br.com.hoffmoney_backend.modelo.usuario.Usuario;
import br.com.hoffmoney_backend.util.entity.EntidadeAuditavel;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.SQLRestriction;

import com.fasterxml.jackson.annotation.JsonFormat;

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

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "usuario_id") // Nome da coluna de chave estrangeira
    private Usuario usuario;

    @Column(length = 100)
    private String nome;

    @Column(length = 255)
    private String descricao;

    @Column
    private Double valor;

    @ManyToOne
    @JoinColumn(name = "categoria_receita_id")
    private CategoriaReceita categoriaReceita;
    
    @Column
    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate dataDeCobranca;

    @Column
    private Boolean paga;
}