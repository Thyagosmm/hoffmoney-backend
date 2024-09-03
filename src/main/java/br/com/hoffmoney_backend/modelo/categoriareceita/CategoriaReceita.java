package br.com.hoffmoney_backend.modelo.categoriareceita;

import br.com.hoffmoney_backend.modelo.receita.Receita;
import br.com.hoffmoney_backend.util.entity.EntidadeAuditavel;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import java.util.List;
import org.hibernate.annotations.SQLRestriction;

@Entity
@Table(name = "CategoriaReceita")
@SQLRestriction("habilitado = true")
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CategoriaReceita extends EntidadeAuditavel {

    @Column(nullable = false, length = 100)
    private String descricaoCategoriaReceita;

    @JsonIgnore
    @OneToMany(mappedBy = "categoriaReceita", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Receita> receitas;
}
