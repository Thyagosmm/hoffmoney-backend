package br.com.hoffmoney_backend.modelo.categoriareceita;

import br.com.hoffmoney_backend.modelo.receita.Receita;
import br.com.hoffmoney_backend.util.entity.EntidadeAuditavel;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.SQLRestriction;


import java.util.List;

@Entity
@Table(name = "CategoriaReceita")
@SQLRestriction("habilitado = true")
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CategoriaReceita extends EntidadeAuditavel {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String descricaoCategoriaReceita;

    @OneToMany(mappedBy = "categoriaReceita")
    private List<Receita> receitas;
}
