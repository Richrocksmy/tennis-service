package org.richrocksmy.tennisservice.health;

import org.eclipse.microprofile.health.HealthCheckResponse;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class SimpleHealthCheckTest {

    @Test
    public void shouldReturnHealthy() {
        SimpleHealthCheck simpleHealthCheck = new SimpleHealthCheck();

        HealthCheckResponse healthCheckResponse = simpleHealthCheck.call();

        assertThat(healthCheckResponse.getState()).isEqualTo(HealthCheckResponse.State.UP);
        assertThat(healthCheckResponse.getName()).isEqualTo("Healthy");
    }

}