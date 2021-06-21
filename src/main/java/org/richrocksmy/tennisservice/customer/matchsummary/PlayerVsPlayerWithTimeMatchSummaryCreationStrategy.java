package org.richrocksmy.tennisservice.customer.matchsummary;

import org.richrocksmy.tennisservice.match.MatchResponse;

import javax.enterprise.context.ApplicationScoped;
import java.time.Duration;
import java.time.ZonedDateTime;

import static java.lang.String.format;

@ApplicationScoped
public class PlayerVsPlayerWithTimeMatchSummaryCreationStrategy implements MatchSummaryCreationStrategy {

    private static final String MATCH_STARTED_SUMMARY_TEMPLATE = "%s vs %s, started %s minutes ago";
    private static final String MATCH_NOT_STARTED_SUMMARY_TEMPLATE = "%s vs %s, starts in %s minutes";

    @Override
    public String getName() {
        return PlayerVsPlayerWithTimeMatchSummaryCreationStrategy.class.getName();
    }

    @Override
    public boolean canCreateMatchSummary(final SummaryType summaryType) {
        return SummaryType.AvBTime.equals(summaryType);
    }

    @Override
    public String createMatchSummary(final MatchResponse matchResponse) {
        ZonedDateTime now = ZonedDateTime.now();
        ZonedDateTime matchStartDateTime = matchResponse.getStartDate();

        if(now.isBefore(matchStartDateTime)) {
            return formatTemplate(MATCH_NOT_STARTED_SUMMARY_TEMPLATE, matchResponse, Duration.between(now, matchStartDateTime).toMinutes());
        } else {
            return formatTemplate(MATCH_STARTED_SUMMARY_TEMPLATE, matchResponse, Duration.between(matchStartDateTime, now).toMinutes());
        }
    }

    private String formatTemplate(final String template, final MatchResponse matchResponse, final long minutesDuration) {
        return format(template,  matchResponse.getPlayerA(), matchResponse.getPlayerB(), minutesDuration);
    }
}
