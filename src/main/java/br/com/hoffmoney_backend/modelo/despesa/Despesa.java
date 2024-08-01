package br.com.hoffmoney_backend.modelo.despesa;

import br.com.hoffmoney_backend.modelo.usuario.Usuario;
import br.com.hoffmoney_backend.util.entity.EntidadeAuditavel;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.SQLRestriction;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDate;

@Entity
@Table(name = "Despesa")
@SQLRestriction("habilitado = true")
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Despesa extends EntidadeAuditavel {

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

    @Column(length = 50)
    private String categoria;
    
    @Column
    private Boolean recorrente;

    @Column
    private Integer vezes;

    @Column(length = 20)
    private String periodo;

    @Column
    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate dataDeCobranca;

    @Column
    private Boolean paga;
}