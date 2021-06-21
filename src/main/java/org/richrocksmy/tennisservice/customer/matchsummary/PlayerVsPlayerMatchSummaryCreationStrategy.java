package org.richrocksmy.tennisservice.customer.matchsummary;

import org.richrocksmy.tennisservice.match.MatchResponse;

import javax.enterprise.context.ApplicationScoped;

import static java.lang.String.format;

@ApplicationScoped
public class PlayerVsPlayerMatchSummaryCreationStrategy implements MatchSummaryCreationStrategy {

    private static final String SUMMARY_TEMPLATE = "%s vs %s";

    @Override
    public String getName() {
        return PlayerVsPlayerMatchSummaryCreationStrategy.class.getName();
    }

    @Override
    public boolean canCreateMatchSummary(final SummaryType summaryType) {
        return SummaryType.AvB.equals(summaryType);
    }

    @Override
    public String createMatchSummary(final MatchResponse matchResponse) {
        return format(SUMMARY_TEMPLATE, matchResponse.getPlayerA(), matchResponse.getPlayerB());
    }
}
