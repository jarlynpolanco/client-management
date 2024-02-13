package com.banreservas.dtos;

import com.banreservas.utils.RegexConstants;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import org.hibernate.validator.constraints.Length;

public class ClientUpdateRequestDto {
    @Email(message = "Debe enviar un email valido")
    public String email;

    public String address;

    @Length(min = 10, max = 10, message = "El telefono se encuentra incorrecto")
    @Pattern(regexp = RegexConstants.JUST_NUMBERS, message = "El phone contiene un formato invalido")
    public String phone;

    @Length(min = 2, max = 2, message = "El codigo de pais se encuentra incorrecto")
    @Pattern(regexp = RegexConstants.JUST_LETTERS, message = "El country contiene un formato invalido")
    public String country;
}
