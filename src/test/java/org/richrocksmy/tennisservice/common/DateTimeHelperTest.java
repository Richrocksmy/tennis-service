package org.richrocksmy.tennisservice.common;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class DateTimeHelperTest {

    @Test
    public void shouldAdjustTimeAndZoneBehindUtcInSameDayToUtc() {
        ZonedDateTime chicagoZonedDateTime = LocalDateTime.of(2021, 06, 21, 13, 10, 00).atZone(ZoneId.of("America/Chicago"));
        ZonedDateTime returnedUtcZonedDateTime = DateTimeHelper.adjustTimeAndZoneToUtc(chicagoZonedDateTime);
        ZonedDateTime expectedUtcZonedDateTime = LocalDateTime.of(2021, 06, 21, 18, 10, 00).atZone(ZoneOffset.UTC);

        assertThat(expectedUtcZonedDateTime).isEqualTo(returnedUtcZonedDateTime);
    }

    @Test
    public void shouldAdjustTimeAndZoneBehindUtcInDifferentDayToUtc() {
        ZonedDateTime localZonedDateTime = LocalDateTime.of(2021, 06, 21, 23, 21, 00).atZone(ZoneId.of("America/Chicago"));
        ZonedDateTime returnedUtcZonedDateTime = DateTimeHelper.adjustTimeAndZoneToUtc(localZonedDateTime);
        ZonedDateTime expectedUtcZonedDateTime = LocalDateTime.of(2021, 06, 22, 4, 21, 00).atZone(ZoneOffset.UTC);

        assertThat(expectedUtcZonedDateTime).isEqualTo(returnedUtcZonedDateTime);
    }

    @Test
    public void shouldAdjustTimeAndZoneAheadOfUtcInSameDayToUtc() {
        ZonedDateTime localZonedDateTime = LocalDateTime.of(2021, 06, 21, 18, 29, 00).atZone(ZoneId.of("Australia/Tasmania"));
        ZonedDateTime returnedUtcZonedDateTime = DateTimeHelper.adjustTimeAndZoneToUtc(localZonedDateTime);
        ZonedDateTime expectedUtcZonedDateTime = LocalDateTime.of(2021, 06, 21, 8, 29, 00).atZone(ZoneOffset.UTC);

        assertThat(returnedUtcZonedDateTime).isEqualTo(expectedUtcZonedDateTime);
    }

    @Test
    public void shouldAdjustTimeAndZoneAheadOfUtcInDifferentDay() {
        ZonedDateTime localZonedDateTime = LocalDateTime.of(2021, 06, 22, 8, 8, 00).atZone(ZoneId.of("Australia/Tasmania"));
        ZonedDateTime returnedUtcZonedDateTime = DateTimeHelper.adjustTimeAndZoneToUtc(localZonedDateTime);
        ZonedDateTime expectedUtcZonedDateTime = LocalDateTime.of(2021, 06, 21, 22, 8, 00).atZone(ZoneOffset.UTC);

        assertThat(expectedUtcZonedDateTime).isEqualTo(returnedUtcZonedDateTime);
    }
}