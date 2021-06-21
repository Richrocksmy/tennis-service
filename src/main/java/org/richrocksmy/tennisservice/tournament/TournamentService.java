package org.richrocksmy.tennisservice.tournament;

import lombok.extern.slf4j.Slf4j;
import org.richrocksmy.tennisservice.match.CreateMatchRequest;
import org.richrocksmy.tennisservice.match.Match;
import org.richrocksmy.tennisservice.match.MatchMapper;

import javax.enterprise.context.ApplicationScoped;
import javax.transaction.Transactional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import static org.richrocksmy.tennisservice.common.DateTimeHelper.adjustTimeAndZoneToUtc;

@Slf4j
@ApplicationScoped
public class TournamentService {

    private final MatchMapper matchMapper;

    public TournamentService(final MatchMapper matchMapper) {
        this.matchMapper = matchMapper;
    }

    @Transactional
    public UUID createTournament(final CreateTournamentRequest createTournamentRequest) {
        log.debug("Creating tennis tournament");
        UUID tournamentId = UUID.randomUUID();

        Set<Match> matches = addTournamentIdAndMatchIdToMatches(tournamentId, createTournamentRequest.getMatches());

        Tournament.builder()
            .startDate(adjustTimeAndZoneToUtc(createTournamentRequest.getStartDate()))
            .matches(matches)
            .tournamentId(tournamentId).build().persist();

        log.debug("Created tennis tournament - {}", tournamentId);
        return tournamentId;
    }

    private Set<Match> addTournamentIdAndMatchIdToMatches(final UUID tournamentId, final Set<CreateMatchRequest> createMatchRequests) {
        return createMatchRequests.stream()
            .map(matchMapper::toMatchBuilder)
            .map(matchBuilder -> matchBuilder.tournamentId(tournamentId))
            .map(matchBuilder -> matchBuilder.matchId(UUID.randomUUID()))
            .map(Match.MatchBuilder::build)
            .collect(Collectors.toSet());
    }
}
