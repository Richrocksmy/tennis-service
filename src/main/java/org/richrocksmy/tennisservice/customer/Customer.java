package org.richrocksmy.tennisservice.customer;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.richrocksmy.tennisservice.match.Match;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Customer extends PanacheEntityBase {

    @Id
    private UUID customerId;
    private String companyName;

    @OneToMany
    @Builder.Default
    private Set<Match> matches = new HashSet<>();

    public void addMatch(final Match match) {
        if(!matches.contains(match)) {
            matches.add(match);
        }
    }
}
