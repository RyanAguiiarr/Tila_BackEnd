package tecnologi.tila.tila.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tecnologi.tila.tila.entity.LogAuditoria;
import tecnologi.tila.tila.entity.Usuario;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public interface LogAuditoriaRepository extends JpaRepository<LogAuditoria, Long> {

    List<LogAuditoria> findByUsuarioIdOrderByDataHoraDesc(UUID usuarioId);

    List<LogAuditoria> findByDataHoraBetween(LocalDateTime inicio, LocalDateTime fim);
}
