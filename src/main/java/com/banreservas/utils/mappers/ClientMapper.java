package com.banreservas.utils.mappers;

import com.banreservas.dtos.ClientCreationRequestDto;
import com.banreservas.dtos.ClientResponseDto;
import com.banreservas.entities.Client;

import java.util.List;

public class ClientMapper {
    private ClientMapper() {
    }

    public static Client mapClientDtoToEntity(final ClientCreationRequestDto clientCreationRequestDto) {
        Client client = new Client();
        client.setFirstName(clientCreationRequestDto.firstName);
        client.setMiddleName(clientCreationRequestDto.middleName);
        client.setLastName(clientCreationRequestDto.lastName);
        client.setSecondSurname(clientCreationRequestDto.secondSurname);
        client.setEmail(clientCreationRequestDto.email);
        client.setAddress(clientCreationRequestDto.address);
        client.setPhone(clientCreationRequestDto.phone);
        client.setCountry(clientCreationRequestDto.country);
        return client;
    }

    public static ClientResponseDto mapClientToDto(final Client client) {
        ClientResponseDto clientResponseDto = new ClientResponseDto();
        clientResponseDto.Id = client.getId();
        clientResponseDto.firstName = client.getFirstName();
        clientResponseDto.middleName = client.getMiddleName();
        clientResponseDto.lastName = client.getLastName();
        clientResponseDto.secondSurname = client.getSecondSurname();
        clientResponseDto.email = client.getEmail();
        clientResponseDto.address = client.getAddress();
        clientResponseDto.phone = client.getPhone();
        clientResponseDto.country = client.getCountry();
        clientResponseDto.demonym = client.getDemonym();
        return clientResponseDto;
    }

    public static List<ClientResponseDto> mapClientsToDtos(final List<Client> clients) {
        return clients.stream().map(ClientMapper::mapClientToDto).toList();
    }
}
