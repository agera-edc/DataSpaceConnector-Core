package org.eclipse.dataspaceconnector.spi.system.health;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.eclipse.dataspaceconnector.spi.result.AbstractResult;

import java.util.Collection;
import java.util.List;

/**
 * This represents a summary status of connector runtime. Every registered provider will contribute their respective status,
 * so this object may (and most likely will) contain multiple health check results.
 */
public class HealthStatus {
    private final List<HealthCheckResult> componentResults;

    public HealthStatus(HealthCheckResult... componentResults) {
        this.componentResults = List.of(componentResults);
    }

    public HealthStatus(Collection<HealthCheckResult> componentResults) {
        this.componentResults = List.copyOf(componentResults);
    }

    public List<HealthCheckResult> getComponentResults() {
        return componentResults;
    }

    @JsonProperty("isSystemHealthy")
    public boolean isHealthy() {
        if (componentResults.isEmpty()) {
            return false;
        }
        return componentResults.stream().allMatch(AbstractResult::succeeded);
    }
}
