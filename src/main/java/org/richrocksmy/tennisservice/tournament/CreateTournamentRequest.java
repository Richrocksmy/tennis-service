package org.richrocksmy.tennisservice.tournament;

import lombok.Data;
import org.richrocksmy.tennisservice.match.CreateMatchRequest;

import java.time.ZonedDateTime;
import java.util.Set;

@Data
public class CreateTournamentRequest {

    private ZonedDateTime startDate;
    private Set<CreateMatchRequest> matches;

}
