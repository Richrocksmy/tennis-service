package org.richrocksmy.tennisservice.match;

import lombok.extern.slf4j.Slf4j;

import javax.enterprise.context.ApplicationScoped;
import javax.transaction.Transactional;
import java.util.UUID;

@Slf4j
@Transactional
@ApplicationScoped
public class MatchService {

    private final MatchMapper matchMapper;

    public MatchService(final MatchMapper matchMapper) {
        this.matchMapper = matchMapper;
    }

    public UUID createMatch(final CreateMatchRequest createMatchRequest) {
        log.debug("Creating new tennis match");
        Match match = matchMapper.toMatchBuilder(createMatchRequest).matchId(UUID.randomUUID()).build();
        match.persist();

        UUID matchId = match.getMatchId();
        log.debug("Created tennis match - {}", matchId);
        return matchId;
    }
}
