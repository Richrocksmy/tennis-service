package org.richrocksmy.tennisservice.customer;

import lombok.extern.slf4j.Slf4j;
import org.richrocksmy.tennisservice.customer.matchsummary.MatchSummaryCreationStrategy;
import org.richrocksmy.tennisservice.customer.matchsummary.SummaryType;
import org.richrocksmy.tennisservice.match.Match;
import org.richrocksmy.tennisservice.match.MatchMapper;
import org.richrocksmy.tennisservice.match.MatchResponse;
import org.richrocksmy.tennisservice.tournament.Tournament;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Any;
import javax.enterprise.inject.Instance;
import javax.transaction.Transactional;
import javax.ws.rs.BadRequestException;
import javax.ws.rs.NotFoundException;
import java.util.Set;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;

@Slf4j
@Transactional
@ApplicationScoped
public class CustomerService {

    private MatchMapper matchMapper;

    private Instance<MatchSummaryCreationStrategy> matchSummaryCreationStrategies;

    public CustomerService(final MatchMapper matchMapper, @Any final Instance<MatchSummaryCreationStrategy> matchSummaryCreationStrategies) {
        this.matchMapper = matchMapper;
        this.matchSummaryCreationStrategies = matchSummaryCreationStrategies;
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

    public Set<MatchResponse> retrieveAllMatchesForCustomer(final UUID customerId, SummaryType summaryType) {
        log.debug("Retrieving all matches for customer - {}", customerId);
        Customer customer = (Customer) Customer.findByIdOptional(customerId).orElseThrow(NotFoundException::new);

        Function<MatchResponse, MatchResponse> matchSummaryAugmenter = createMatchSummaryAugmenter(summaryType);

        return customer.getMatches().stream()
            .map(matchMapper::toMatchResponse)
            .map(matchSummaryAugmenter)
            .collect(Collectors.toSet());
    }

    private Function<MatchResponse, MatchResponse> createMatchSummaryAugmenter(final SummaryType summaryType) {
        MatchSummaryCreationStrategy summaryCreationStrategy = matchSummaryCreationStrategies.stream()
            .filter(matchSummaryCreationStrategy -> matchSummaryCreationStrategy.canCreateMatchSummary(summaryType))
            .findFirst().orElseThrow(() -> new RuntimeException("Could not find valid match summary creation strategy!"));

        log.debug("Using {} creation strategy", summaryCreationStrategy.getName());

        return matchResponse -> {
            String summary = summaryCreationStrategy.createMatchSummary(matchResponse);
            return matchResponse.toBuilder().summary(summary).build();
        };
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
