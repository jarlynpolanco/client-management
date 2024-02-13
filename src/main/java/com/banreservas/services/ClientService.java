package com.banreservas.services;

import com.banreservas.dtos.ClientCreationRequestDto;
import com.banreservas.dtos.ClientResponseDto;
import com.banreservas.dtos.ClientUpdateRequestDto;
import com.banreservas.entities.Client;
import com.banreservas.utils.StringUtils;
import com.banreservas.utils.mappers.ClientMapper;
import com.banreservas.repositories.ClientRepository;
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
    CountriesApiClientService countriesApiClientService;

    @Transactional
    public ClientResponseDto createClient(final ClientCreationRequestDto clientCreationRequestDto) {
        Client client = ClientMapper.mapClientDtoToEntity(clientCreationRequestDto);
        try {
            client.setCountry(clientCreationRequestDto.country.toUpperCase());
            client.setDemonym(countriesApiClientService.getDemonymByCountryCode(client.getCountry()));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        client.persist();
        return ClientMapper.mapClientToDto(client);
    }

    public List<ClientResponseDto> getAllClients() {
        List<Client> clients = clientRepository.listAll();
        return ClientMapper.mapClientsToDtos(clients);
    }

    public List<ClientResponseDto> getClientsByCountry(final String country) {
        List<Client> clients = clientRepository.list("country", country.toUpperCase());
        return ClientMapper.mapClientsToDtos(clients);
    }

    public ClientResponseDto getClientById(final Long id) {
        return ClientMapper.mapClientToDto(getClient(id));
    }

    @Transactional
    public void updateClient(final Long id, final ClientUpdateRequestDto clientUpdateRequestDto) {
        Client client = getClient(id);
        client.setEmail(StringUtils.stringIsNullOrEmptyValidation(clientUpdateRequestDto.email, client.getEmail()));
        client.setAddress(StringUtils.stringIsNullOrEmptyValidation(clientUpdateRequestDto.address, client.getAddress()));
        client.setPhone(StringUtils.stringIsNullOrEmptyValidation(clientUpdateRequestDto.phone, client.getPhone()));
        client.setCountry(StringUtils.stringIsNullOrEmptyValidation(clientUpdateRequestDto.country, client.getCountry()).toUpperCase());
        try {
            client.setDemonym(countriesApiClientService.getDemonymByCountryCode(client.getCountry()));
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
            throw new BadRequestException("El cliente: "+ id + " no existe.");
        }
        return client;
    }
}
