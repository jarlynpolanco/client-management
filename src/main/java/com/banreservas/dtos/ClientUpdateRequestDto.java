package com.banreservas.dtos;

import jakarta.validation.constraints.Email;
import org.hibernate.validator.constraints.Length;

public class ClientUpdateRequestDto {
    @Email(message = "Debe enviar un email valido")
    public String email;

    public String address;

    @Length(min = 10, max = 10, message = "El telefono se encuentra incorrecto")
    public String phone;

    @Length(min = 2, max = 2, message = "El codigo de pais se encuentra incorrecto")
    public String country;
}
