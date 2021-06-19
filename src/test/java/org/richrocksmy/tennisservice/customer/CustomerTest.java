package org.richrocksmy.tennisservice.customer;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.richrocksmy.tennisservice.match.Match;

import java.util.Set;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class CustomerTest {

    @Test
    public void shouldAddMatchToCustomerIfCustomerDoesNotExist() {
        Customer customer = Customer.builder().build();
        Match match = Match.builder().matchId(UUID.randomUUID()).build();

        customer.addMatch(match);
        Set<Match> matches = customer.getMatches();

        assertThat(matches).hasSize(1);
        assertThat(matches).contains(match);
    }

    @Test
    public void shouldNotAddMatchToCustomerIfCustomerExists() {
        Customer customer = Customer.builder().build();
        Match match = Match.builder().matchId(UUID.randomUUID()).build();

        customer.addMatch(match);
        customer.addMatch(match);
        Set<Match> matches = customer.getMatches();

        assertThat(matches).hasSize(1);
        assertThat(matches).contains(match);
    }

}