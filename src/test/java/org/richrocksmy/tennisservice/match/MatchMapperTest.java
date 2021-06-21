package org.richrocksmy.tennisservice.match;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class MatchMapperTest {

    @Test
    public void shouldMapToMatchBuilder() {
        ZonedDateTime now = ZonedDateTime.now();

        CreateMatchRequest createMatchRequest = new CreateMatchRequest();
        createMatchRequest.setPlayerA("PlayerA");
        createMatchRequest.setPlayerB("PlayerB");
        createMatchRequest.setStartDate(now);

        Match match = new MatchMapper().toMatchBuilder(createMatchRequest).build();

        assertThat(match.getPlayerA()).isEqualTo("PlayerA");
        assertThat(match.getPlayerB()).isEqualTo("PlayerB");
        assertThat(match.getStartDate()).isEqualTo(now);
    }

    @Test
    public void shouldMapToMatchBuilderAndNormalizeStartTimeToUtc() {
        ZonedDateTime now = LocalDateTime.now().atZone(ZoneOffset.UTC);

        CreateMatchRequest createMatchRequest = new CreateMatchRequest();
        createMatchRequest.setPlayerA("PlayerA");
        createMatchRequest.setPlayerB("PlayerB");
        createMatchRequest.setStartDate(now);

        Match match = new MatchMapper().toMatchBuilder(createMatchRequest).build();

        assertThat(match.getPlayerA()).isEqualTo("PlayerA");
        assertThat(match.getPlayerB()).isEqualTo("PlayerB");
        assertThat(match.getStartDate()).isEqualTo(now);
    }

    @Test
    public void shouldMapToMatchResponse() {
        ZonedDateTime now = ZonedDateTime.now();
        UUID matchId = UUID.randomUUID();

        Match match = Match.builder()
            .matchId(matchId)
            .playerA("PlayerA")
            .playerB("PlayerB")
            .startDate(now).build();

        MatchResponse matchResponse = new MatchMapper().toMatchResponse(match);

        assertThat(matchResponse.getMatchId()).isEqualTo(matchId);
        assertThat(matchResponse.getPlayerA()).isEqualTo("PlayerA");
        assertThat(matchResponse.getPlayerB()).isEqualTo("PlayerB");
        assertThat(matchResponse.getStartDate()).isEqualTo(now);
    }
}