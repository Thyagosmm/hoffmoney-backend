<<<<<<< HEAD
package br.com.hoffmoney_backend.modelo.usuario;

import org.hibernate.annotations.SQLRestriction;
import br.com.hoffmoney_backend.util.entity.EntidadeAuditavel;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "Usuario")
@SQLRestriction("habilitado = true")
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Usuario extends EntidadeAuditavel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 50)
    private String nome;

    @Column(length = 100, unique = true)
    private String email;

    @Column(length = 100)
    private String senha;
}
=======
package br.com.hoffmoney_backend.modelo.usuario;

import org.hibernate.annotations.SQLRestriction;

import br.com.hoffmoney_backend.util.entity.EntidadeAuditavel;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "Usuario")
@SQLRestriction("habilitado = true")
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Usuario extends EntidadeAuditavel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 50)
    private String nome;

    @Column(length = 100, unique = true)
    private String email;

    @Column(length = 100)
    private String senha;

    @Column(length = 50)
    @Builder.Default
    private Double saldo = 0.0;

}
>>>>>>> 9ec99eb3483f0917d7ae03f09dd63caf99b48036
