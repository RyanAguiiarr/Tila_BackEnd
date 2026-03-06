package tecnologi.tila.tila.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record DadosCadastroMedico(
       @NotBlank @Email String email,
       @NotBlank String senha,
       @NotBlank String crm,
       @NotBlank String nome,
       @NotBlank String especialidade) {
}
