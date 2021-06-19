package org.richrocksmy.tennisservice.tournament;

import io.quarkus.panache.mock.PanacheMock;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.junit.mockito.InjectSpy;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.richrocksmy.tennisservice.match.CreateMatchRequest;
import org.richrocksmy.tennisservice.match.Match;
import org.richrocksmy.tennisservice.match.MatchMapper;

import javax.inject.Inject;
import java.time.ZonedDateTime;
import java.util.Set;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@QuarkusTest
class TournamentServiceTest {

    @InjectSpy
    private MatchMapper matchMapper;

    @Inject
    private TournamentService tournamentService;

    @Test
    public void shouldCreateTournament() {
        CreateTournamentRequest createTournamentRequest = new CreateTournamentRequest();
        ZonedDateTime now = ZonedDateTime.now();
        createTournamentRequest.setStartDate(now);
        createTournamentRequest.setMatches(createCreateMatchRequests(now));

        Tournament.TournamentBuilder tournamentBuilder = mock(Tournament.TournamentBuilder.class);
        PanacheMock.mock(Tournament.class);
        Tournament tournament = mock(Tournament.class);

        when(Tournament.builder()).thenReturn(tournamentBuilder);
        when(tournamentBuilder.startDate(any())).thenReturn(tournamentBuilder);
        when(tournamentBuilder.matches(any())).thenReturn(tournamentBuilder);
        when(tournamentBuilder.tournamentId(any())).thenReturn(tournamentBuilder);
        when(tournamentBuilder.build()).thenReturn(tournament);

        tournamentService.createTournament(createTournamentRequest);

        verify(tournamentBuilder).startDate(now);
        verify(tournamentBuilder).tournamentId(any(UUID.class));

        ArgumentCaptor<Set<Match>> setArgumentCaptor = ArgumentCaptor.forClass(Set.class);
        verify(tournamentBuilder).matches(setArgumentCaptor.capture());

        Set<Match> result = setArgumentCaptor.getValue();
        assertThat(result).hasSize(3);
        assertThat(result).extracting("startDate", "playerA", "playerB")
            .contains(tuple(now, "PlayerA", "PlayerB"),
                tuple(now, "Player2", "Player5"),
                tuple(now, "PlayerD", "PlayerF"));
    }

    private Set<CreateMatchRequest> createCreateMatchRequests(final ZonedDateTime zonedDateTime) {
        return Set.of(getCreateMatchRequest(zonedDateTime, "PlayerA", "PlayerB"),
            getCreateMatchRequest(zonedDateTime, "Player2", "Player5"),
            getCreateMatchRequest(zonedDateTime, "PlayerD", "PlayerF"));
    }

    private CreateMatchRequest getCreateMatchRequest(final ZonedDateTime zonedDateTime, final String playerA, final String playerB) {
        CreateMatchRequest createMatchRequest = new CreateMatchRequest();
        createMatchRequest.setStartDate(zonedDateTime);
        createMatchRequest.setPlayerA(playerA);
        createMatchRequest.setPlayerB(playerB);

        return createMatchRequest;
    }

}