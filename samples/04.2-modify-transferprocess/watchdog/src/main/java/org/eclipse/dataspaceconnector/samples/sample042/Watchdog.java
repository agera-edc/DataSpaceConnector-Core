package org.eclipse.dataspaceconnector.samples.sample042;

import org.eclipse.dataspaceconnector.spi.monitor.Monitor;
import org.eclipse.dataspaceconnector.spi.transfer.TransferProcessManager;
import org.eclipse.dataspaceconnector.spi.types.domain.transfer.TransferProcessStates;

import java.time.Duration;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Watchdog {

    private final TransferProcessManager manager;
    private final Monitor monitor;
    private ScheduledExecutorService executor;

    public Watchdog(TransferProcessManager manager, Monitor monitor) {

        this.manager = manager;
        this.monitor = monitor;
    }

    public void start() {
        executor = Executors.newSingleThreadScheduledExecutor();
        // run every 10 minutes, no initial delay
        executor.scheduleAtFixedRate(this::check, 10, 10, TimeUnit.SECONDS);
    }

    public void stop() {
        if (executor != null && !executor.isShutdown()) {
            executor.shutdownNow();
        }
    }

    private void check() {
        monitor.info("Running watchdog - submit command");
        manager.enqueueCommand(new CheckTransferProcessTimeoutCommand(3, TransferProcessStates.IN_PROGRESS, Duration.ofSeconds(10)));
    }
}
