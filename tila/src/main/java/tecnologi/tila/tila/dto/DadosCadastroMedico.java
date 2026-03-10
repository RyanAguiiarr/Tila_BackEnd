package tecnologi.tila.tila.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record DadosCadastroMedico(
       @NotBlank(message = "campo email não pode ser nulo.")
       @Email
       String email,
       @NotBlank(message = "campo senha não pode ser nulo.")
       String senha,
       @NotBlank(message = "campo crm não pode ser nulo.")
       String crm,
       @NotBlank(message = "campo nome não pode ser nulo.")
       String nome,
       @NotBlank(message = "campo especialidade não pode ser nulo.")
       String especialidade) {
}
