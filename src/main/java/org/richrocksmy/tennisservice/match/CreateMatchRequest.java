package org.richrocksmy.tennisservice.match;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.time.ZonedDateTime;

@Data
public class CreateMatchRequest {

    @NotNull
    private ZonedDateTime startDate;

    @NotNull
    private String playerA;

    @NotNull
    private String playerB;

}
