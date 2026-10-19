package com.example.saas.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class WorkshopUpdateRequestDTO {

    @Pattern(
            regexp = ".*\\S.*",
            message = "O nome da oficina não pode ficar vazio."
    )
    @Size(max = 100, message = "Nome deve ter no máximo 100 caracteres.")
    private String name;

    @Pattern(
            regexp = ".*\\S.*",
            message = "O telefone não pode ficar vazio."
    )
    @Size(max = 20, message = "Telefone deve ter no máximo 20 caracteres.")
    private String phone;

    @Email(message = "Informe um e-mail válido.")
    @Size(max = 150, message = "E-mail deve ter no máximo 150 caracteres.")
    @Pattern(
            regexp = ".*\\S.*",
            message = "O e-mail não pode ficar vazio."
    )
    private String email;
}