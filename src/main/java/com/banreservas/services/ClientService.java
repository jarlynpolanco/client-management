package com.banreservas.services;

import com.banreservas.dtos.ClientCreationRequestDto;
import com.banreservas.dtos.ClientResponseDto;
import com.banreservas.dtos.ClientUpdateRequestDto;
import com.banreservas.entities.Client;
import com.banreservas.repositories.ClientRepository;
import com.banreservas.utils.RestCountriesApiClient;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.BadRequestException;

import java.util.List;

@ApplicationScoped
public class ClientService {
    @Inject
    ClientRepository clientRepository;

    @Inject
    RestCountriesApiClient restCountriesApiClient;

    @Transactional
    public ClientResponseDto createClient(final ClientCreationRequestDto clientCreationRequestDto) {
        Client client = mapClientDtoToEntity(clientCreationRequestDto);
        try {
            client.setCountry(clientCreationRequestDto.country.toUpperCase());
            client.setDemonym(restCountriesApiClient.getDemonymByCountryCode(client.getCountry()));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        client.persist();
        return mapClientToDto(client);
    }

    public List<ClientResponseDto> getAllClients() {
        List<Client> clients = clientRepository.listAll();
        return mapClientsToDtos(clients);
    }

    public List<ClientResponseDto> getClientsByCountry(final String country) {
        if(country == null || country.length() != 2) {
            throw new BadRequestException("La country enviada no es valida");
        }
        List<Client> clients = clientRepository.list("country", country.toUpperCase());
        return mapClientsToDtos(clients);
    }

    public ClientResponseDto getClientById(final Long id) {
        return mapClientToDto(getClient(id));
    }

    @Transactional
    public void updateClient(final Long id, final ClientUpdateRequestDto clientUpdateRequestDto) {
        Client client = getClient(id);
        client.setEmail(stringIsNullOrEmptyValidation(clientUpdateRequestDto.email, client.getEmail()));
        client.setAddress(stringIsNullOrEmptyValidation(clientUpdateRequestDto.address, client.getAddress()));
        client.setPhone(stringIsNullOrEmptyValidation(clientUpdateRequestDto.phone, client.getPhone()));
        client.setCountry(stringIsNullOrEmptyValidation(clientUpdateRequestDto.country, client.getCountry()).toUpperCase());
        try {
            client.setDemonym(restCountriesApiClient.getDemonymByCountryCode(client.getCountry()));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Transactional
    public void deleteClientById(final Long id) {
        clientRepository.delete(getClient(id));
    }

    private Client getClient(final Long id) {
        Client client = clientRepository.findById(id);
        if(client == null) {
            throw new BadRequestException("El cliente: "+ id + " no existe");
        }
        return client;
    }


    private List<ClientResponseDto> mapClientsToDtos(final List<Client> clients) {
        return clients.stream().map(this::mapClientToDto).toList();
    }

    private Client mapClientDtoToEntity(final ClientCreationRequestDto clientCreationRequestDto) {
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

    private ClientResponseDto mapClientToDto(final Client client) {
        ClientResponseDto clientResponseDto = new ClientResponseDto();
        clientResponseDto.Id = client.id;
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

    private String stringIsNullOrEmptyValidation(final String newValue, final String oldValue) {
        if(newValue == null || newValue.isEmpty()) {
            return oldValue;
        } else {
            return newValue;
        }
    }
}
