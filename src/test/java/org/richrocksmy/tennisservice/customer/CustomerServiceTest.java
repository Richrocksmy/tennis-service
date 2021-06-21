package org.richrocksmy.tennisservice.customer;

import io.quarkus.panache.mock.PanacheMock;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.junit.mockito.InjectMock;
import io.quarkus.test.junit.mockito.InjectSpy;
import org.junit.jupiter.api.Test;
import org.richrocksmy.tennisservice.customer.matchsummary.PlayerVsPlayerMatchSummaryCreationStrategy;
import org.richrocksmy.tennisservice.customer.matchsummary.SummaryType;
import org.richrocksmy.tennisservice.match.Match;
import org.richrocksmy.tennisservice.match.MatchMapper;
import org.richrocksmy.tennisservice.match.MatchResponse;
import org.richrocksmy.tennisservice.tournament.Tournament;

import javax.inject.Inject;
import javax.ws.rs.BadRequestException;
import javax.ws.rs.NotFoundException;
import java.time.ZonedDateTime;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@QuarkusTest
class CustomerServiceTest {

    @InjectSpy
    private MatchMapper matchMapper;

    @InjectMock
    private PlayerVsPlayerMatchSummaryCreationStrategy playerVsPlayerMatchSummaryCreationStrategy
        = mock(PlayerVsPlayerMatchSummaryCreationStrategy.class);

    @Inject
    private CustomerService customerService;

    @Test
    public void shouldCreateCustomer() {
        CreateCustomerRequest createCustomerRequest = new CreateCustomerRequest();
        createCustomerRequest.setCompanyName("Company Name");
        PanacheMock.mock(Customer.class);

        UUID customerId = UUID.randomUUID();
        Customer customer = mock(Customer.class);
        Customer.CustomerBuilder customerBuilder = mock(Customer.CustomerBuilder.class);

        when(Customer.builder()).thenReturn(customerBuilder);
        when(customerBuilder.companyName(any())).thenReturn(customerBuilder);
        when(customerBuilder.customerId(any())).thenReturn(customerBuilder);
        when(customerBuilder.build()).thenReturn(customer);
        when(customer.getCustomerId()).thenReturn(customerId);

        UUID id = customerService.createCustomer(createCustomerRequest);
        assertThat(id).isEqualTo(customerId);
        verify(customerBuilder).companyName("Company Name");
        verify(customerBuilder).customerId(any(UUID.class));
        verify(customer).persist();
    }

    @Test
    public void shouldThrowNotFoundExceptionWhenRetrievingAllMatchesForUserWhenUserDoesNotExist() {
        PanacheMock.mock(Customer.class);
        when(Customer.findByIdOptional(any())).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> customerService.retrieveAllMatchesForCustomer(UUID.randomUUID(), SummaryType.AvB));
    }

    @Test
    public void shouldRetrieveAllMatchesForUserWhenUserExists() {
        PanacheMock.mock(Customer.class);
        Customer customer = mock(Customer.class);

        ZonedDateTime now = ZonedDateTime.now();
        when(customer.getMatches()).thenReturn(createMatches(now));

        when(Customer.findByIdOptional(any())).thenReturn(Optional.of(customer));
        when(playerVsPlayerMatchSummaryCreationStrategy.getName()).thenReturn("Name");
        when(playerVsPlayerMatchSummaryCreationStrategy.canCreateMatchSummary(SummaryType.AvB)).thenReturn(true);
        when(playerVsPlayerMatchSummaryCreationStrategy.createMatchSummary(any())).thenReturn("Match Summary");

        Set<MatchResponse> matchResponses = customerService.retrieveAllMatchesForCustomer(UUID.randomUUID(), SummaryType.AvB);

        assertThat(matchResponses).hasSize(3);
                    assertThat(matchResponses).extracting("startDate", "playerA", "playerB", "summary")
            .contains(tuple(now, "PlayerA", "PlayerB", "Match Summary"),
                      tuple(now, "PlayerA", "PlayerB", "Match Summary"),
                      tuple(now, "PlayerA", "PlayerB", "Match Summary"));

        verify(playerVsPlayerMatchSummaryCreationStrategy).getName();
        verify(playerVsPlayerMatchSummaryCreationStrategy).canCreateMatchSummary(SummaryType.AvB);
        verify(playerVsPlayerMatchSummaryCreationStrategy, times(3)).createMatchSummary(any());
    }

    @Test
    public void shouldThrowExceptionWhenCreatingLicenceForMatchWhenCustomerDoesNotExist() {
        PanacheMock.mock(Customer.class);
        when(Customer.findByIdOptional(any())).thenReturn(Optional.empty());

        PanacheMock.mock(Match.class);
        Match match = mock(Match.class);
        when(Match.findByIdOptional(any())).thenReturn(Optional.of(match));

        CreateLicenceRequest createLicenceRequest = new CreateLicenceRequest();
        createLicenceRequest.setEventId(UUID.randomUUID());

        assertThrows(NotFoundException.class, () -> {
            customerService.createLicenceForMatch(UUID.randomUUID(), createLicenceRequest);
        });
    }

    @Test
    public void shouldThrowExceptionWhenCreatingLicenceForMatchWhenMatchDoesNotExist() {
        PanacheMock.mock(Customer.class);
        when(Customer.findByIdOptional(any())).thenReturn(Optional.empty());

        CreateLicenceRequest createLicenceRequest = new CreateLicenceRequest();
        createLicenceRequest.setEventId(UUID.randomUUID());

        assertThrows(BadRequestException.class, () -> {
            customerService.createLicenceForMatch(UUID.randomUUID(), createLicenceRequest);
        });

    }

    @Test
    public void shouldCreateLicenceForMatchWhenCustomerAndMatchExist() {
        PanacheMock.mock(Customer.class);
        Customer customer = mock(Customer.class);
        when(Customer.findByIdOptional(any())).thenReturn(Optional.of(customer));

        PanacheMock.mock(Match.class);
        Match match = mock(Match.class);
        when(Match.findByIdOptional(any())).thenReturn(Optional.of(match));

        CreateLicenceRequest createLicenceRequest = new CreateLicenceRequest();
        createLicenceRequest.setEventId(UUID.randomUUID());

        customerService.createLicenceForMatch(UUID.randomUUID(), createLicenceRequest);

        verify(customer).addMatch(match);
    }

    @Test
    public void shouldThrowExceptionWhenCreatingLicenceForTournamentWhenCustomerDoesNotExist() {
        PanacheMock.mock(Customer.class);
        when(Customer.findByIdOptional(any())).thenReturn(Optional.empty());

        PanacheMock.mock(Tournament.class);
        Tournament tournament = mock(Tournament.class);
        when(Tournament.findByIdOptional(any())).thenReturn(Optional.of(tournament));

        CreateLicenceRequest createLicenceRequest = new CreateLicenceRequest();
        createLicenceRequest.setEventId(UUID.randomUUID());

        assertThrows(NotFoundException.class, () -> {
            customerService.createLicenceForTournament(UUID.randomUUID(), createLicenceRequest);
        });
    }

    @Test
    public void shouldThrowExceptionWhenCreatingLicenceForTournamentWhenTournamentDoesNotExist() {
        PanacheMock.mock(Customer.class);
        when(Customer.findByIdOptional(any())).thenReturn(Optional.empty());

        CreateLicenceRequest createLicenceRequest = new CreateLicenceRequest();
        createLicenceRequest.setEventId(UUID.randomUUID());

        assertThrows(BadRequestException.class, () -> {
            customerService.createLicenceForTournament(UUID.randomUUID(), createLicenceRequest);
        });

    }

    @Test
    public void shouldCreateLicenceForMatchWhenCustomerAndTournamentExist() {
        PanacheMock.mock(Customer.class);
        Customer customer = mock(Customer.class);
        when(Customer.findByIdOptional(any())).thenReturn(Optional.of(customer));

        PanacheMock.mock(Tournament.class);
        Tournament tournament = mock(Tournament.class);
        ZonedDateTime now = ZonedDateTime.now();
        when(tournament.getMatches()).thenReturn(createMatches(now));
        when(Tournament.findByIdOptional(any())).thenReturn(Optional.of(tournament));

        CreateLicenceRequest createLicenceRequest = new CreateLicenceRequest();
        createLicenceRequest.setEventId(UUID.randomUUID());

        customerService.createLicenceForTournament(UUID.randomUUID(), createLicenceRequest);

        verify(customer, times(3)).addMatch(any());
    }

    public Set<Match> createMatches(final ZonedDateTime now) {
        Match.MatchBuilder matchBuilder = Match.builder()
            .tournamentId(UUID.randomUUID())
            .startDate(now)
            .playerA("PlayerA")
            .playerB("PlayerB");

        return Set.of(matchBuilder.matchId(UUID.randomUUID()).build(),
            matchBuilder.matchId(UUID.randomUUID()).build(),
            matchBuilder.matchId(UUID.randomUUID()).build());
    }

}


