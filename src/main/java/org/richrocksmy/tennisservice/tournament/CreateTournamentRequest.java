package org.richrocksmy.tennisservice.tournament;

import lombok.Data;
import org.richrocksmy.tennisservice.match.CreateMatchRequest;

import javax.validation.constraints.NotNull;
import java.time.ZonedDateTime;
import java.util.Set;

@Data
public class CreateTournamentRequest {

    @NotNull
    private ZonedDateTime startDate;

    @NotNull
    private Set<CreateMatchRequest> matches;

}
