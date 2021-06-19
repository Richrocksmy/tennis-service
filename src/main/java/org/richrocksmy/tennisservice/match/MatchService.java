package org.richrocksmy.tennisservice.match;

import javax.enterprise.context.ApplicationScoped;
import javax.transaction.Transactional;
import java.util.UUID;

@ApplicationScoped
public class MatchService {

    private final MatchMapper matchMapper;

    public MatchService(final MatchMapper matchMapper) {
        this.matchMapper = matchMapper;
    }

    @Transactional
    public UUID createMatch(final CreateMatchRequest createMatchRequest) {
        Match match = matchMapper.toMatchBuilder(createMatchRequest).matchId(UUID.randomUUID()).build();
        match.persist();

        return match.getMatchId();
    }
}
