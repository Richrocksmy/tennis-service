package org.richrocksmy.tennisservice.customer.matchsummary;

import org.richrocksmy.tennisservice.match.MatchResponse;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class UnspecifiedMatchSummaryCreationStrategy implements MatchSummaryCreationStrategy {

    @Override
    public String getName() {
        return UnspecifiedMatchSummaryCreationStrategy.class.getName();
    }

    @Override
    public boolean canCreateMatchSummary(final SummaryType summaryType) {
        return summaryType == null;
    }

    @Override
    public String createMatchSummary(final MatchResponse matchResponse) {
        return null;
    }
}
