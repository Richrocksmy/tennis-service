package org.richrocksmy.tennisservice.customer;

import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.junit.mockito.InjectMock;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.richrocksmy.tennisservice.match.MatchResponse;

import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;
import java.time.ZonedDateTime;
import java.util.Set;
import java.util.UUID;

import static io.restassured.RestAssured.given;
import static java.lang.String.format;
import static org.hamcrest.Matchers.containsString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@QuarkusTest
public class CustomerResourceTest {

    private Jsonb jsonb;

    @InjectMock
    private CustomerService customerService;

    @BeforeEach
    public void setup() {
        jsonb = JsonbBuilder.create();
    }

    @Test
    public void shouldReturn201AndLocationOnCreateCustomer() {
        UUID customerId = UUID.randomUUID();
        when(customerService.createCustomer(any())).thenReturn(customerId);

        CreateCustomerRequest createCustomerRequest = new CreateCustomerRequest();
        createCustomerRequest.setCompanyName("Acme Sports Content");

        given().contentType("application/json").body(jsonb.toJson(createCustomerRequest))
            .when().post("/v1/customers")
            .then()
            .statusCode(201)
            .header("Location", format("http://localhost:8081/v1/customers/%s", customerId));

        verify(customerService).createCustomer(createCustomerRequest);
    }

    @Test
    public void shouldReturn400WhenInvalidRequestOnCreateCustomer() {
        UUID customerId = UUID.randomUUID();
        when(customerService.createCustomer(any())).thenReturn(customerId);

        CreateCustomerRequest createCustomerRequest = new CreateCustomerRequest();

        given().contentType("application/json").body(jsonb.toJson(createCustomerRequest))
            .when().post("/v1/customers")
            .then()
            .statusCode(400);
    }

    @Test
    public void shouldReturn201OnCreateLicenceForMatch() {
        UUID customerId = UUID.randomUUID();

        CreateLicenceRequest createLicenceRequest = new CreateLicenceRequest();
        createLicenceRequest.setEventId(UUID.randomUUID());

        given().contentType("application/json").body(jsonb.toJson(createLicenceRequest))
            .when().patch(format("/v1/customers/%s/licence/match", customerId))
            .then()
            .statusCode(201);

        verify(customerService).createLicenceForMatch(customerId, createLicenceRequest);
    }

    @Test
    public void shouldReturn400WhenInvalidRequestOnCreateLicenceForMatch() {
        UUID customerId = UUID.randomUUID();
        CreateLicenceRequest createLicenceRequest = new CreateLicenceRequest();

        given().contentType("application/json").body(jsonb.toJson(createLicenceRequest))
            .when().patch(format("/v1/customers/%s/licence/match", customerId))
            .then()
            .statusCode(400);
    }

    @Test
    public void shouldReturn201OnCreateLicenceForTournament() {
        UUID customerId = UUID.randomUUID();

        CreateLicenceRequest createLicenceRequest = new CreateLicenceRequest();
        createLicenceRequest.setEventId(UUID.randomUUID());

        given().contentType("application/json").body(jsonb.toJson(createLicenceRequest))
            .when().patch(format("/v1/customers/%s/licence/tournament", customerId))
            .then()
            .statusCode(201);

        verify(customerService).createLicenceForTournament(customerId, createLicenceRequest);
    }

    @Test
    public void shouldReturn400WhenInvalidRequestOnCreateLicenceForTournament() {
        UUID customerId = UUID.randomUUID();

        CreateLicenceRequest createLicenceRequest = new CreateLicenceRequest();

        given().contentType("application/json").body(jsonb.toJson(createLicenceRequest))
            .when().patch(format("/v1/customers/%s/licence/tournament", customerId))
            .then()
            .statusCode(400);
    }

    @Test
    public void shouldReturn200AndAllMatchesForCustomerWithPvPMatchSummary() {
        UUID customerId = UUID.randomUUID();
        Set<MatchResponse> matches = Set.of(MatchResponse.builder()
            .matchId(UUID.randomUUID())
            .startDate(ZonedDateTime.now())
            .playerA("Player A")
            .playerB("Player B")
            .summary("Match Summary")
            .build());

        when(customerService.retrieveAllMatchesForCustomer(eq(customerId), any())).thenReturn(matches);

        given()
            .when().get(format("/v1/customers/%s/matches?summaryType=AvB", customerId))
            .then()
            .statusCode(200)
            .body(containsString(jsonb.toJson(matches)));
    }

    @Test
    public void shouldReturn200AndAllMatchesForCustomerWithPvPTimeMatchSummary() {
        UUID customerId = UUID.randomUUID();
        Set<MatchResponse> matches = Set.of(MatchResponse.builder()
            .matchId(UUID.randomUUID())
            .startDate(ZonedDateTime.now())
            .playerA("Player A")
            .playerB("Player B")
            .summary("Match Summary")
            .build());

        when(customerService.retrieveAllMatchesForCustomer(eq(customerId), any())).thenReturn(matches);

        given()
            .when().get(format("/v1/customers/%s/matches?summaryType=AvBTime", customerId))
            .then()
            .statusCode(200)
            .body(containsString(jsonb.toJson(matches)));
    }

    @Test
    public void shouldReturn200AndAllMatchesForCustomerWithoutMatchSummary() {
        UUID customerId = UUID.randomUUID();
        Set<MatchResponse> matches = Set.of(MatchResponse.builder()
            .matchId(UUID.randomUUID())
            .startDate(ZonedDateTime.now())
            .playerA("Player A")
            .playerB("Player B")
            .build());

        when(customerService.retrieveAllMatchesForCustomer(eq(customerId), any())).thenReturn(matches);

        given()
            .when().get(format("/v1/customers/%s/matches", customerId))
            .then()
            .statusCode(200)
            .body(containsString(jsonb.toJson(matches)));
    }

}