package org.richrocksmy.tennisservice.match;

import javax.enterprise.context.ApplicationScoped;

import static org.richrocksmy.tennisservice.common.DateTimeHelper.adjustTimeAndZoneToUtc;

@ApplicationScoped
public class MatchMapper {

    public Match.MatchBuilder toMatchBuilder(final CreateMatchRequest createMatchRequest) {
        return Match.builder()
            .playerA(createMatchRequest.getPlayerA())
            .playerB(createMatchRequest.getPlayerB())
            .startDate(adjustTimeAndZoneToUtc(createMatchRequest.getStartDate()));
    }

    public MatchResponse toMatchResponse(final Match match) {
        return MatchResponse.builder()
            .matchId(match.getMatchId())
            .playerA(match.getPlayerA())
            .playerB(match.getPlayerB())
            .startDate(match.getStartDate()).build();
    }
}
