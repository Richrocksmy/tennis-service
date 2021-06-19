package org.richrocksmy.tennisservice.match;

import lombok.Data;

import java.time.ZonedDateTime;

@Data
public class CreateMatchRequest {

    private ZonedDateTime startDate;
    private String playerA;
    private String playerB;

}
