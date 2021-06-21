package org.richrocksmy.tennisservice.customer.matchsummary;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.richrocksmy.tennisservice.match.MatchResponse;

import java.time.ZonedDateTime;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class PlayerVsPlayerWithTimeMatchSummaryCreationStrategyTest {

    private MatchSummaryCreationStrategy matchSummaryCreationStrategy;

    @BeforeEach
    public void setup() {
        matchSummaryCreationStrategy = new PlayerVsPlayerWithTimeMatchSummaryCreationStrategy();
    }

    @Test
    public void shouldReturnName() {
        assertThat(matchSummaryCreationStrategy.getName()).isEqualTo("org.richrocksmy.tennisservice.customer.matchsummary.PlayerVsPlayerWithTimeMatchSummaryCreationStrategy");
    }

    @Test
    public void shouldBeAbleToHandleSummaryType() {
        assertThat(matchSummaryCreationStrategy.canCreateMatchSummary(SummaryType.AvBTime)).isTrue();
    }


    @Test
    public void shouldNotBeAbleToHandleSummaryType() {
        assertThat(matchSummaryCreationStrategy.canCreateMatchSummary(SummaryType.AvB)).isFalse();
    }

    @Test
    public void shouldReturnCorrectMatchSummaryWhenMatchHasNotStarted() {
        ZonedDateTime thirtyMinutesFromNow = ZonedDateTime.now().plusMinutes(30).plusSeconds(59);

        MatchResponse matchResponse = MatchResponse.builder().playerA("Player A").playerB("Player B").startDate(thirtyMinutesFromNow).build();
        assertThat(matchSummaryCreationStrategy.createMatchSummary(matchResponse)).isEqualTo("Player A vs Player B, starts in 30 minutes");
    }

    @Test
    public void shouldReturnCorrectMatchSummaryWhenMatchHasStarted() {
        ZonedDateTime thirtyMinutesFromNow = ZonedDateTime.now().minusMinutes(30).minusSeconds(59);

        MatchResponse matchResponse = MatchResponse.builder().playerA("Player A").playerB("Player B").startDate(thirtyMinutesFromNow).build();
        assertThat(matchSummaryCreationStrategy.createMatchSummary(matchResponse)).isEqualTo("Player A vs Player B, started 30 minutes ago");
    }

}