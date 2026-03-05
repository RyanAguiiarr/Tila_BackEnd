package tecnologi.tila.tila.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "medicos")
public class Medico {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nomeCompleto;

    @Column(nullable = false, unique = true)
    private String crm;

    private String especialidade;

    @Lob // para salvar em Base64
    private String assinaturaDigital;

    @OneToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;
}
