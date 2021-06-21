package org.richrocksmy.tennisservice.customer.matchsummary;

import org.richrocksmy.tennisservice.match.MatchResponse;

public interface MatchSummaryCreationStrategy {

    String getName();

    boolean canCreateMatchSummary(final SummaryType summaryType);

    String createMatchSummary(final MatchResponse matchResponse);
}
