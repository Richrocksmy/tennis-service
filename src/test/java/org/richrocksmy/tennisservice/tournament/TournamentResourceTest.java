package org.richrocksmy.tennisservice.tournament;

import io.quarkus.test.junit.QuarkusTest;
import org.richrocksmy.tennisservice.match.CreateMatchRequest;

import java.time.ZonedDateTime;
import java.util.Set;

import static io.restassured.RestAssured.given;

@QuarkusTest
public class TournamentResourceTest {

//    @Test
    public void testCreateTournamentEndpoint() {
        CreateTournamentRequest createTournamentRequest = new CreateTournamentRequest();
        createTournamentRequest.setStartDate(ZonedDateTime.now());

        CreateMatchRequest createMatchRequest = new CreateMatchRequest();
        createMatchRequest.setStartDate(ZonedDateTime.now());
        createMatchRequest.setPlayerA("Player A");
        createMatchRequest.setPlayerB("Player B");
        createTournamentRequest.setMatches(Set.of(createMatchRequest));

        given().body(createTournamentRequest)
            .when().post("/v1/tournaments")
            .then()
            .statusCode(200);
    }

}