package org.richrocksmy.tennisservice.customer;

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

@ApplicationScoped
public class CustomerService {

    private final MatchMapper matchMapper;

    public CustomerService(final MatchMapper matchMapper) {
        this.matchMapper = matchMapper;
    }

    @Transactional
    public UUID createCustomer(final CreateCustomerRequest createCustomerRequest) {
        Customer customer = Customer.builder()
            .companyName(createCustomerRequest.getCompanyName())
            .customerId(UUID.randomUUID()).build();

        customer.persist();

        return customer.getCustomerId();
    }

    @Transactional
    public Set<MatchResponse> retrieveAllMatchesForCustomer(final UUID customerId) {
        Customer customer = (Customer) Customer.findByIdOptional(customerId).orElseThrow(NotFoundException::new);

        return customer.getMatches().stream()
            .map(matchMapper::toMatchResponse)
            .collect(Collectors.toSet());
    }

    @Transactional
    public void createLicenceForMatch(final UUID customerId, final CreateLicenceRequest createLicenceRequest) {
        Match match = (Match) Match.findByIdOptional(createLicenceRequest.getEventId()).orElseThrow(BadRequestException::new);
        Customer customer = (Customer) Customer.findByIdOptional(customerId).orElseThrow(NotFoundException::new);

        customer.addMatch(match);
    }

    @Transactional
    public void createLicenceForTournament(final UUID customerId, final CreateLicenceRequest createLicenceRequest) {
        Tournament tournament = (Tournament) Tournament.findByIdOptional(createLicenceRequest.getEventId())
            .orElseThrow(BadRequestException::new);
        Customer customer = (Customer) Customer.findByIdOptional(customerId).orElseThrow(NotFoundException::new);

        tournament.getMatches().stream()
            .forEach(match -> customer.addMatch(match));
    }

}
