package org.richrocksmy.tennisservice.customer.matchsummary;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.richrocksmy.tennisservice.match.MatchResponse;

import java.time.ZonedDateTime;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class PlayerVPlayerMatchSummaryCreationStrategyTest {

    private MatchSummaryCreationStrategy matchSummaryCreationStrategy;

    @BeforeEach
    public void setup() {
        matchSummaryCreationStrategy = new PlayerVsPlayerMatchSummaryCreationStrategy();
    }

    @Test
    public void shouldReturnName() {
        assertThat(matchSummaryCreationStrategy.getName()).isEqualTo("org.richrocksmy.tennisservice.customer.matchsummary.PlayerVsPlayerMatchSummaryCreationStrategy");
    }

    @Test
    public void shouldBeAbleToHandleSummaryType() {
        assertThat(matchSummaryCreationStrategy.canCreateMatchSummary(SummaryType.AvB)).isTrue();
    }


    @Test
    public void shouldNotBeAbleToHandleSummaryType() {
        assertThat(matchSummaryCreationStrategy.canCreateMatchSummary(SummaryType.AvBTime)).isFalse();
    }

    @Test
    public void shouldReturnCorrectMatchSummary() {
        ZonedDateTime now = ZonedDateTime.now();
        MatchResponse matchResponse = MatchResponse.builder().playerA("Player A").playerB("Player B").startDate(now).build();
        assertThat(matchSummaryCreationStrategy.createMatchSummary(matchResponse)).isEqualTo("Player A vs Player B");
    }
}