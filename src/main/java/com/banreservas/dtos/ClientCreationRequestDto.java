package com.banreservas.dtos;

import io.smallrye.common.constraint.NotNull;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.Length;

public class ClientCreationRequestDto {
    @NotNull
    @NotBlank(message = "El firstName no puede estar en blanco")
    public String firstName;

    public String middleName;

    @NotNull
    @NotBlank(message = "El lastName no puede estar en blanco")
    public String lastName;

    public String secondSurname;

    @NotNull
    @NotBlank(message = "El email no puede estar en blanco")
    @Email(message = "Debe enviar un email valido")
    public String email;

    @NotNull
    @NotBlank(message = "El address no puede estar en blanco")
    public String address;

    @NotNull
    @NotBlank(message = "El phone no puede estar en blanco")
    @Length(min = 10, max = 10, message = "El telefono se encuentra incorrecto")
    public String phone;

    @NotNull
    @NotBlank(message = "El country no puede estar en blanco")
    @Length(min = 2, max = 2, message = "El codigo de pais se encuentra incorrecto")
    public String country;
}
