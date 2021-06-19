package org.richrocksmy.tennisservice.customer;

import lombok.extern.slf4j.Slf4j;
import org.richrocksmy.tennisservice.match.Match;
import org.richrocksmy.tennisservice.match.MatchMapper;
import org.richrocksmy.tennisservice.match.MatchResponse;
import org.richrocksmy.tennisservice.tournament.Tournament;

import javax.enterprise.context.ApplicationScoped;
import javax.transaction.Transactional;
import javax.ws.rs.BadRequestException;
import javax.ws.rs.NotFoundException;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Transactional
@ApplicationScoped
public class CustomerService {

    private final MatchMapper matchMapper;

    public CustomerService(final MatchMapper matchMapper) {
        this.matchMapper = matchMapper;
    }

    public UUID createCustomer(final CreateCustomerRequest createCustomerRequest) {
        log.debug("Creating customer");
        Customer customer = Customer.builder()
            .companyName(createCustomerRequest.getCompanyName())
            .customerId(UUID.randomUUID()).build();

        customer.persist();

        UUID customerId = customer.getCustomerId();
        log.debug("Created customer - {}", customerId);

        return customerId;
    }

    public Set<MatchResponse> retrieveAllMatchesForCustomer(final UUID customerId) {
        log.debug("Retrieving all matches for customer - {}", customerId);
        Customer customer = (Customer) Customer.findByIdOptional(customerId).orElseThrow(NotFoundException::new);

        return customer.getMatches().stream()
            .map(matchMapper::toMatchResponse)
            .collect(Collectors.toSet());
    }

    public void createLicenceForMatch(final UUID customerId, final CreateLicenceRequest createLicenceRequest) {
        log.debug("Creating match licence for customer - {}", customerId);

        Match match = (Match) Match.findByIdOptional(createLicenceRequest.getEventId()).orElseThrow(BadRequestException::new);
        Customer customer = (Customer) Customer.findByIdOptional(customerId).orElseThrow(NotFoundException::new);

        customer.addMatch(match);
    }

    public void createLicenceForTournament(final UUID customerId, final CreateLicenceRequest createLicenceRequest) {
        log.debug("Creating tournament licence for customer - {}", customerId);

        Tournament tournament = (Tournament) Tournament.findByIdOptional(createLicenceRequest.getEventId())
            .orElseThrow(BadRequestException::new);
        Customer customer = (Customer) Customer.findByIdOptional(customerId).orElseThrow(NotFoundException::new);

        tournament.getMatches().stream()
            .forEach(match -> customer.addMatch(match));
    }

}
