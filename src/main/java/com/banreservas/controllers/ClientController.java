package com.banreservas.controllers;

import com.banreservas.dtos.ClientCreationRequestDto;
import com.banreservas.dtos.ClientResponseDto;
import com.banreservas.dtos.ClientUpdateRequestDto;
import com.banreservas.services.ClientService;
import io.quarkus.runtime.annotations.RegisterForReflection;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;

@Path("/api/clients")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@RegisterForReflection
public class ClientController {

    @Inject
    ClientService clientService;

    @POST()
    @Path("/create")
    public Response createClient(@Valid ClientCreationRequestDto clientCreationRequestDto) {
        return Response.status(Response.Status.CREATED)
                .entity(clientService.createClient(clientCreationRequestDto))
                .build();
    }

    @GET
    @Path("/get-all")
    public List<ClientResponseDto> getAllClients() {
        return clientService.getAllClients();
    }

    @GET
    @Path("/by-country/{country}")
    public List<ClientResponseDto> getClientsByCountry(@PathParam("country") String country) {
        return clientService.getClientsByCountry(country);
    }

    @GET
    @Path("/get-by/{id}")
    public ClientResponseDto getClientById(@PathParam("id") Long id) {
        return clientService.getClientById(id);
    }

    @PUT
    @Path("/update-by/{id}")
    @Transactional
    public void updateClient(@PathParam("id") Long id, @Valid ClientUpdateRequestDto clientUpdateRequestDto) {
        clientService.updateClient(id, clientUpdateRequestDto);
    }

    @DELETE
    @Path("/delete-by/{id}")
    @Transactional
    public void deleteClient(@PathParam("id") Long id) {
        clientService.deleteClientById(id);
    }
}
