package org.richrocksmy.tennisservice.match;

import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.junit.mockito.InjectSpy;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;
import java.time.ZonedDateTime;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@QuarkusTest
public class MatchServiceTest {

    @InjectSpy
    private MatchMapper matchMapper;

    @Inject
    private MatchService matchService;

    @Test
    public void shouldCreateMatch() {
        CreateMatchRequest createMatchRequest = new CreateMatchRequest();

        ZonedDateTime now = ZonedDateTime.now();
        createMatchRequest.setStartDate(now);
        createMatchRequest.setPlayerA("PlayerA");
        createMatchRequest.setPlayerB("PlayerB");

        UUID matchId = UUID.randomUUID();
        Match match = mock(Match.class);
        Match.MatchBuilder matchBuilder = mock(Match.MatchBuilder.class);

        when(matchBuilder.matchId(any())).thenReturn(matchBuilder);
        when(matchBuilder.build()).thenReturn(match);
        when(match.getMatchId()).thenReturn(matchId);

        when(matchMapper.toMatchBuilder(createMatchRequest)).thenReturn(matchBuilder);
        UUID id = matchService.createMatch(createMatchRequest);

        assertThat(id).isEqualTo(matchId);
        verify(matchBuilder).matchId(any(UUID.class));
        verify(match).persist();
    }
}
