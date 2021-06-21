package org.richrocksmy.tennisservice.customer.matchsummary;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.richrocksmy.tennisservice.match.MatchResponse;

import java.time.ZonedDateTime;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class UnspecifiedMatchSummaryCreationStrategyTest {

    private MatchSummaryCreationStrategy matchSummaryCreationStrategy;

    @BeforeEach
    public void setup() {
        matchSummaryCreationStrategy = new UnspecifiedMatchSummaryCreationStrategy();
    }

    @Test
    public void shouldReturnName() {
        assertThat(matchSummaryCreationStrategy.getName()).isEqualTo("org.richrocksmy.tennisservice.customer.matchsummary.UnspecifiedMatchSummaryCreationStrategy");
    }

    @Test
    public void shouldBeAbleToHandleSummaryType() {
        assertThat(matchSummaryCreationStrategy.canCreateMatchSummary(null)).isTrue();
    }


    @Test
    public void shouldNotBeAbleToHandleSummaryType() {
        assertThat(matchSummaryCreationStrategy.canCreateMatchSummary(SummaryType.AvB)).isFalse();
    }

    @Test
    public void shouldReturnCorrectMatchSummary() {
        ZonedDateTime now = ZonedDateTime.now();
        MatchResponse matchResponse = MatchResponse.builder().playerA("Player A").playerB("Player B").startDate(now).build();
        assertThat(matchSummaryCreationStrategy.createMatchSummary(matchResponse)).isNull();
    }

}