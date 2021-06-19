package org.richrocksmy.tennisservice.tournament;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.richrocksmy.tennisservice.match.Match;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Tournament extends PanacheEntityBase {

    @Id
    private UUID tournamentId;
    private ZonedDateTime startDate;

    @OneToMany(cascade = {CascadeType.ALL})
    @Builder.Default
    private Set<Match> matches = new HashSet<>();

}

