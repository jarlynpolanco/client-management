package com.banreservas.dtos;

import com.banreservas.utils.RegexConstants;
import io.smallrye.common.constraint.NotNull;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import org.hibernate.validator.constraints.Length;

public class ClientCreationRequestDto {
    @NotNull
    @Pattern(regexp = RegexConstants.JUST_LETTERS, message = "El nombre contiene un formato invalido")
    @NotBlank(message = "El firstName no puede estar en blanco")
    public String firstName;

    @Pattern(regexp = RegexConstants.JUST_LETTERS, message = "El nombre contiene un formato invalido")
    public String middleName;

    @NotNull
    @Pattern(regexp = RegexConstants.JUST_LETTERS_WITH_SPACES, message = "El nombre contiene un formato invalido")
    @NotBlank(message = "El lastName no puede estar en blanco")
    public String lastName;

    @Pattern(regexp = RegexConstants.JUST_LETTERS_WITH_SPACES, message = "El nombre contiene un formato invalido")
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
    @Pattern(regexp = RegexConstants.JUST_NUMBERS, message = "El phone contiene un formato invalido")
    @Length(min = 10, max = 10, message = "El telefono se encuentra incorrecto")
    public String phone;

    @NotNull
    @NotBlank(message = "El country no puede estar en blanco")
    @Pattern(regexp = RegexConstants.JUST_LETTERS, message = "El country contiene un formato invalido")
    @Length(min = 2, max = 2, message = "El codigo de pais se encuentra incorrecto")
    public String country;
}
