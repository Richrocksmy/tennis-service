package org.richrocksmy.tennisservice.tournament;

import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.junit.mockito.InjectMock;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.richrocksmy.tennisservice.match.CreateMatchRequest;

import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;
import java.time.ZonedDateTime;
import java.util.Set;
import java.util.UUID;

import static io.restassured.RestAssured.given;
import static java.lang.String.format;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@QuarkusTest
public class TournamentResourceTest {

    private Jsonb jsonb;

    @InjectMock
    private TournamentService tournamentService;

    @BeforeEach
    public void setup() {
        jsonb = JsonbBuilder.create();
    }

    @Test
    public void shouldReturn201AndLocationOnCreateTournament() {
        UUID tournamentId = UUID.randomUUID();
        when(tournamentService.createTournament(any())).thenReturn(tournamentId);
        CreateTournamentRequest createTournamentRequest = new CreateTournamentRequest();
        createTournamentRequest.setStartDate(ZonedDateTime.now());

        CreateMatchRequest createMatchRequest = new CreateMatchRequest();
        createMatchRequest.setStartDate(ZonedDateTime.now());
        createMatchRequest.setPlayerA("Player A");
        createMatchRequest.setPlayerB("Player B");
        createTournamentRequest.setMatches(Set.of(createMatchRequest));

        given().contentType("application/json").body(jsonb.toJson(createTournamentRequest))
            .when().post("/v1/tournaments")
            .then()
            .statusCode(201)
            .header("Location", format("http://localhost:8081/v1/tournaments/%s", tournamentId));

        verify(tournamentService).createTournament(createTournamentRequest);
    }

    @Test
    public void shouldReturn400WhenRequestIsInvalid() {
        CreateTournamentRequest createTournamentRequest = new CreateTournamentRequest();
        createTournamentRequest.setStartDate(ZonedDateTime.now());

        given().contentType("application/json").body(jsonb.toJson(createTournamentRequest))
            .when().post("/v1/tournaments")
            .then()
            .statusCode(400);
    }
}