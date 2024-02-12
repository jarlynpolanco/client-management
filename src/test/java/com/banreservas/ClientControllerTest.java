package com.banreservas;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.number.OrderingComparison.greaterThanOrEqualTo;

@QuarkusTest
public class ClientControllerTest {
    private Integer clientId;


    @Test
    public void testCreateClientEndpoint() {
        Response response =
                given()
                        .contentType(ContentType.JSON)
                        .body("{\"firstName\": \"Jarlyn\", \"lastName\": \"Jarlyn\",\"email\": \"jarlynpolanco@gmail.com\", \"address\": \"c/oval 2 #6\", \"phone\": \"8492832210\", \"country\": \"DO\"}")
                        .when()
                        .post("/api/clients/create");
        response.then()
                .statusCode(201);

        clientId =
                response.jsonPath().get("Id");

    }

    @Test
    public void testCreateClientBadRequest() {
        given()
                .contentType(ContentType.JSON)
                .body("{\"firstName\": \"Jarlyn\", \"email\": \"jarlynpolanco@gmail.com\", \"address\": \"c/oval 2 #6\", \"phone\": \"849-283-2210\", \"country\": \"DO\"}")
                .when()
                .post("/api/clients/create")
                .then()
                .statusCode(400);
    }

    @Test
    public void testGetAllClientsEndpoint() {
        given()
                .when()
                .get("/api/clients/get-all")
                .then()
                .statusCode(200)
                .body("$.size()", greaterThanOrEqualTo(1));
    }

    @Test
    public void testGetClientsByCountryEndpoint() {
        given()
                .when()
                .get("/api/clients/by-country/LB")
                .then()
                .statusCode(200)
                .body("$.size()", is(0));
    }

    @Test
    public void testGetClientsByCountryWithNonExistentCountry() {
        given()
                .when()
                .get("/api/clients/by-country/LU")
                .then()
                .statusCode(200)
                .body("$.size()", is(0));
    }

    @Test
    public void testGetClientsByCountryWithWrongCountry() {
        given()
                .when()
                .get("/api/clients/by-country/KRASDA")
                .then()
                .statusCode(400);
    }

    @Test
    public void testGetClientByIdWithNonExistentId() {
        Long clientId = 0L;
        given()
                .when()
                .get("/api/clients/get-by/" + this.clientId)
                .then()
                .statusCode(400);
    }

    @Test
    public void testUpdateClientEndpoint() {
        given()
                .contentType(ContentType.JSON)
                .body("{\"email\": \"jarlynpolancod@outlook.com\", \"address\": \"C/Oval 2\", \"phone\": \"8492832210\", \"country\": \"US\"}")
                .when()
                .put("/api/clients/update-by/" + this.clientId)
                .then()
                .statusCode(204);
    }

    @Test
    public void testUpdateClientWithNonExistentId() {
        Long clientId = 0L;
        given()
                .contentType(ContentType.JSON)
                .body("{\"email\": \"jarlynpolancod@outlook.com\", \"address\": \"C/Oval 2\", \"phone\": \"8492832210\", \"country\": \"US\"}")
                .when()
                .put("/api/clients/update-by/" + clientId)
                .then()
                .statusCode(400);
    }


    @Test
    public void testDeleteClientEndpoint() {
        given()
                .when()
                .delete("/api/clients/delete-by/" + clientId)
                .then()
                .statusCode(204);
    }

    @Test
    public void testDeleteClientEndpointNonExistentId() {
        Long clientId = 0L;
        given()
                .when()
                .delete("/api/clients/delete-by/" + clientId)
                .then()
                .statusCode(400);
    }
}
