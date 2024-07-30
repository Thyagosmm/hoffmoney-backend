package br.com.hoffmoney_backend.modelo.despesa;

import br.com.hoffmoney_backend.modelo.usuario.Usuario;
import br.com.hoffmoney_backend.util.entity.EntidadeAuditavel;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.SQLRestriction;

import java.math.BigDecimal;
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

    @Column(length = 100)
    private String descricao;

    @Column(precision = 10, scale = 2)
    private BigDecimal valor;

    private LocalDate data;

    @Column(length = 50)
    private String categoria;

    @Column(nullable = false)
    private boolean recorrente;

    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;
}