package com.example.saas.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class WorkshopRegistrationRequestDTO {

    @NotBlank(message = "Nome da oficina é obrigatório.")
    @Size(max = 100, message = "Nome da oficina deve ter no máximo 100 caracteres.")
    private String workshopName;

    @NotBlank(message = "Telefone da oficina é obrigatório.")
    @Size(max = 20, message = "Telefone deve ter no máximo 20 caracteres.")
    private String workshopPhone;

    @Email(message = "E-mail da oficina inválido.")
    @Size(max = 150, message = "E-mail da oficina deve ter no máximo 150 caracteres.")
    private String workshopEmail;

    @NotBlank(message = "Nome do proprietário é obrigatório.")
    @Size(max = 100, message = "Nome do proprietário deve ter no máximo 100 caracteres.")
    private String ownerName;

    @NotBlank(message = "E-mail do proprietário é obrigatório.")
    @Email(message = "E-mail do proprietário inválido.")
    @Size(max = 150, message = "E-mail deve ter no máximo 150 caracteres.")
    private String ownerEmail;

    @NotBlank(message = "Senha é obrigatória.")
    @Size(min = 8, max = 100, message = "Senha deve ter entre 8 e 100 caracteres.")
    private String ownerPassword;
    
}