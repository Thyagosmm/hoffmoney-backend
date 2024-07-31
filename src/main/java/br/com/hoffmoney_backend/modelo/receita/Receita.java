package br.com.hoffmoney_backend.modelo.receita;

import java.time.LocalDate;

import org.hibernate.annotations.SQLRestriction;
import br.com.hoffmoney_backend.util.entity.EntidadeAuditavel;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
// import jakarta.persistence.FetchType;
// import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
// import java.util.List;

@Entity
@Table(name = "Receita")
@SQLRestriction("habilitado = true")

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class Receita extends EntidadeAuditavel{

    @Column
    private String nome;

    @Column
    private Double valor;

    @Column
    private String categoria;

    // @Column
    // private boolean recorrente;

    @Column
    private LocalDate dataRecebimento;

    @Column
    private String descricao;
    
}
