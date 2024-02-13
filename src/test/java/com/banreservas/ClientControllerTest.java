package com.banreservas;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.jboss.resteasy.spi.HttpResponseCodes;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.number.OrderingComparison.greaterThanOrEqualTo;

@QuarkusTest
public class ClientControllerTest {
    @Test
    @Order(1)
    public void testCreateClientWithRequiredDataSuccessfully() {
        given()
                .contentType(ContentType.JSON)
                .body("{\"firstName\": \"Jarlyn\", \"lastName\": \"Polanco\",\"email\": \"jarlynpolanco@gmail.com\", \"address\": \"c/oval 2 #6\", \"phone\": \"8492832210\", \"country\": \"DO\"}")
                .when()
                .post("/api/clients/create")
                .then()
                .statusCode(HttpResponseCodes.SC_CREATED);
    }

    @Test
    @Order(2)
    public void testCreateClientWithAllDataSuccessfully() {
        given()
                .contentType(ContentType.JSON)
                .body("{\"firstName\": \"Pedro\", \"lastName\": \"Perez\", \"middleName\": \"Hugo\", \"secondSurname\": \"Urbaez\",\"email\": \"pedroperez@gmail.com\", \"address\": \"c/Duarte #21\", \"phone\": \"8092201111\", \"country\": \"DO\"}")
                .when()
                .post("/api/clients/create")
                .then()
                .statusCode(HttpResponseCodes.SC_CREATED);
    }

    @Test
    @Order(2)
    public void testCreateClientWithNumbersInNameAndLastNameBadRequest() {
        given()
                .contentType(ContentType.JSON)
                .body("{\"firstName\": \"P3dro\", \"lastName\": \"P3rez\", \"middleName\": \"Hugo\", \"secondSurname\": \"Urbaez\",\"email\": \"pedroperez@gmail.com\", \"address\": \"c/Duarte #21\", \"phone\": \"8092201111\", \"country\": \"DO\"}")
                .when()
                .post("/api/clients/create")
                .then()
                .statusCode(HttpResponseCodes.SC_BAD_REQUEST);
    }

    @Test
    @Order(2)
    public void testCreateClientWithWrongEmailBadRequest() {
        given()
                .contentType(ContentType.JSON)
                .body("{\"firstName\": \"P3dro\", \"lastName\": \"P3rez\", \"middleName\": \"Hugo\", \"secondSurname\": \"Urbaez\",\"email\": \"pedroperez.com\", \"address\": \"c/Duarte #21\", \"phone\": \"8092201111\", \"country\": \"DO\"}")
                .when()
                .post("/api/clients/create")
                .then()
                .statusCode(HttpResponseCodes.SC_BAD_REQUEST);
    }

    @Test
    public void testCreateClientWithWrongPhoneBadRequest() {
        given()
                .contentType(ContentType.JSON)
                .body("{\"firstName\": \"Jarlyn\", \"email\": \"jarlynpolanco@gmail.com\", \"address\": \"c/oval 2 #6\", \"phone\": \"849-283-2210\", \"country\": \"DO\"}")
                .when()
                .post("/api/clients/create")
                .then()
                .statusCode(HttpResponseCodes.SC_BAD_REQUEST);
    }

    @Test
    public void testCreateClientWithWrongCountryBadRequest() {
        given()
                .contentType(ContentType.JSON)
                .body("{\"firstName\": \"Jarlyn\", \"email\": \"jarlynpolanco@gmail.com\", \"address\": \"c/oval 2 #6\", \"phone\": \"8492832210\", \"country\": \"RDA\"}")
                .when()
                .post("/api/clients/create")
                .then()
                .statusCode(HttpResponseCodes.SC_BAD_REQUEST);
    }

    @Test
    public void testCreateClientWithMissingRequiredFieldsBadRequest() {
        given()
                .contentType(ContentType.JSON)
                .body("{\"email\": \"jarlynpolanco@gmail.com\", \"address\": \"c/oval 2 #6\", \"phone\": \"8492832210\", \"country\": \"RDA\"}")
                .when()
                .post("/api/clients/create")
                .then()
                .statusCode(HttpResponseCodes.SC_BAD_REQUEST);
    }

    @Test
    public void testGetAllClientsSuccessfully() {
        given()
                .when()
                .get("/api/clients/get-all")
                .then()
                .statusCode(HttpResponseCodes.SC_OK)
                .body("$.size()", greaterThanOrEqualTo(1));
    }

    @Test
    public void testGetClientsByCountryWithNonDataOnRequestedCountryCodeSuccessfully() {
        given()
                .when()
                .get("/api/clients/by-country/LB")
                .then()
                .statusCode(HttpResponseCodes.SC_OK)
                .body("$.size()", is(0));
    }

    @Test
    public void testGetClientsByCountryWithNonExistentCountrySuccessfully() {
        given()
                .when()
                .get("/api/clients/by-country/LU")
                .then()
                .statusCode(HttpResponseCodes.SC_OK)
                .body("$.size()", is(0));
    }

    @Test
    public void testGetClientsByCountryWithWrongCountryCodeBadRequest() {
        given()
                .when()
                .get("/api/clients/by-country/LAKS")
                .then()
                .statusCode(HttpResponseCodes.SC_BAD_REQUEST);
    }

    @Test
    public void testGetClientByIdWithNonExistentIdBadRequest() {
        long clientId = 2077378909L;
        given()
                .when()
                .get("/api/clients/get-by/" + clientId)
                .then()
                .statusCode(HttpResponseCodes.SC_BAD_REQUEST);
    }

    @Test
    public void testUpdateClientSuccessfully() {
        given()
                .contentType(ContentType.JSON)
                .body("{\"email\": \"jarlynpolanco@outlook.com\", \"address\": \"C/Oval 2\", \"phone\": \"8492832210\", \"country\": \"US\"}")
                .when()
                .put("/api/clients/update-by/" + getClientId())
                .then()
                .statusCode(HttpResponseCodes.SC_NO_CONTENT);
    }

    @Test
    public void testUpdateClientWithWrongPhoneNumberBadRequest() {
        given()
                .contentType(ContentType.JSON)
                .body("{\"email\": \"jarlynpolanco@outlook.com\", \"address\": \"C/Oval 2\", \"phone\": \"849-283-2210\", \"country\": \"US\"}")
                .when()
                .put("/api/clients/update-by/" + getClientId())
                .then()
                .statusCode(HttpResponseCodes.SC_BAD_REQUEST);
    }

    @Test
    public void testUpdateClientWithNonExistentIdBadRequest() {
        long clientId = 2077378909L;
        given()
                .contentType(ContentType.JSON)
                .body("{\"email\": \"jarlynpolancod@outlook.com\", \"address\": \"C/Oval 2\", \"phone\": \"8492832210\", \"country\": \"US\"}")
                .when()
                .put("/api/clients/update-by/" + clientId)
                .then()
                .statusCode(HttpResponseCodes.SC_BAD_REQUEST);
    }

    @Test
    public void testDeleteClientWithNonExistentId() {
        long clientId = 2077378909L;
        given()
                .when()
                .delete("/api/clients/delete-by/" + clientId)
                .then()
                .statusCode(HttpResponseCodes.SC_BAD_REQUEST);
    }

    @Test
    public void testDeleteClientSuccessfully() {
        given()
                .when()
                .delete("/api/clients/delete-by/" + getClientId())
                .then()
                .statusCode(HttpResponseCodes.SC_NO_CONTENT);
    }

    private Integer getClientId() {
        Response response = given()
                .when()
                .get("/api/clients/by-country/DO");
        return (Integer) response.jsonPath().getList("Id").getFirst();
    }
}
