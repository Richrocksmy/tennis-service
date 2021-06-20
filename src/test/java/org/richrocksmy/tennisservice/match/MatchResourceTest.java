package org.richrocksmy.tennisservice.match;

import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.junit.mockito.InjectMock;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;
import java.time.ZonedDateTime;
import java.util.UUID;

import static io.restassured.RestAssured.given;
import static java.lang.String.format;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@QuarkusTest
public class MatchResourceTest {

    private Jsonb jsonb;

    @InjectMock
    private MatchService matchService;

    @BeforeEach
    public void setup() {
        jsonb = JsonbBuilder.create();
    }

    @Test
    public void shouldReturn201AndLocationOnCreateMatch() {
        UUID matchId = UUID.randomUUID();
        when(matchService.createMatch(any())).thenReturn(matchId);

        CreateMatchRequest createMatchRequest = new CreateMatchRequest();
        createMatchRequest.setStartDate(ZonedDateTime.now());
        createMatchRequest.setPlayerA("Player A");
        createMatchRequest.setPlayerB("Player B");

        given().contentType("application/json").body(jsonb.toJson(createMatchRequest))
            .when().post("/v1/matches")
            .then()
            .statusCode(201)
            .header("Location", format("http://localhost:8081/v1/matches/%s", matchId));

        verify(matchService).createMatch(createMatchRequest);
    }

    @Test
    public void shouldReturn400WhenRequestIsInvalid() {
        CreateMatchRequest createMatchRequest = new CreateMatchRequest();

        given().contentType("application/json").body(jsonb.toJson(createMatchRequest))
            .when().post("/v1/matches")
            .then()
            .statusCode(400);
    }
}