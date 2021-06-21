package org.richrocksmy.tennisservice.common;

import java.time.ZoneOffset;
import java.time.ZonedDateTime;

public class DateTimeHelper {

    public static ZonedDateTime adjustTimeAndZoneToUtc(final ZonedDateTime zonedDateTime) {
        return zonedDateTime.toInstant().atZone(ZoneOffset.UTC);
    }
}
