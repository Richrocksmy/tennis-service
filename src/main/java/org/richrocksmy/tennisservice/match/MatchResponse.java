package org.richrocksmy.tennisservice.match;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.ZonedDateTime;
import java.util.UUID;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MatchResponse {

    private UUID matchId;
    private ZonedDateTime startDate;
    private String playerA;
    private String playerB;
    private String summary;

}
