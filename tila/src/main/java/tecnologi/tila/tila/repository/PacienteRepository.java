package tecnologi.tila.tila.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tecnologi.tila.tila.entity.Paciente;

import java.util.List;
import java.util.Optional;

@Repository
public interface PacienteRepository extends JpaRepository<Paciente, Long> {
    Optional<Paciente> findByCpf(String cpf);

    List<Paciente> findByNomeCompletoContainingIgnoreCase(String nome);
}
