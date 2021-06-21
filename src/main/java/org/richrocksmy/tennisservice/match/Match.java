package org.richrocksmy.tennisservice.match;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.time.ZonedDateTime;
import java.util.UUID;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Match extends PanacheEntityBase {

    @Id
    private UUID matchId;
    private UUID tournamentId;
    private ZonedDateTime startDate;
    private String playerA;
    private String playerB;

}

