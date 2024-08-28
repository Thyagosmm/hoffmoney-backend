package br.com.hoffmoney_backend.modelo.categoriareceita;

import org.hibernate.annotations.SQLRestriction;
import br.com.hoffmoney_backend.util.entity.EntidadeAuditavel;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
    private String descricaoReceita;

}
