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
import static org.mockito.ArgumentMatchers.any;
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
    public void testCreateTournamentEndpoint() {
        when(tournamentService.createTournament(any())).thenReturn(UUID.randomUUID());
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
            .statusCode(201);
    }

}