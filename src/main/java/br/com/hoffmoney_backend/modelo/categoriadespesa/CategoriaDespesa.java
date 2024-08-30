package br.com.hoffmoney_backend.modelo.categoriadespesa;

import br.com.hoffmoney_backend.modelo.despesa.Despesa;
import br.com.hoffmoney_backend.util.entity.EntidadeAuditavel;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.SQLRestriction;

import java.util.List;

@Entity
@Table(name = "CategoriaDespesa")
@SQLRestriction("habilitado = true")
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CategoriaDespesa extends EntidadeAuditavel {

    @Column(nullable = false, length = 100)
    private String descricaoCategoriaDespesa;

    @OneToMany(mappedBy = "categoriaDespesa", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Despesa> despesas;
}
