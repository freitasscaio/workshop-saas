
package com.example.saas.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AppUserRequestDTO {

    @NotBlank(message = "Nome é obrigatório.")
    @Size(max = 100, message = "Nome deve ter no máximo 100 caracteres.")
    private String name;

    @NotBlank(message = "E-mail é obrigatório.")
    @Email(message = "E-mail inválido.")
    @Size(max = 150, message = "E-mail deve ter no máximo 150 caracteres.")
    private String email;

    @NotBlank(message = "Senha é obrigatória.")
    @Size(min = 8, max = 100,
            message = "Senha deve ter entre 8 e 100 caracteres.")
    private String password;
}
