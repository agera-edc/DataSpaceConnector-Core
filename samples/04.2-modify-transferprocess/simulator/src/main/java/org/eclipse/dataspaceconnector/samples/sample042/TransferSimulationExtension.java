package org.eclipse.dataspaceconnector.samples.sample042;

import org.eclipse.dataspaceconnector.spi.system.Inject;
import org.eclipse.dataspaceconnector.spi.system.ServiceExtension;
import org.eclipse.dataspaceconnector.spi.system.ServiceExtensionContext;
import org.eclipse.dataspaceconnector.spi.transfer.store.TransferProcessStore;
import org.eclipse.dataspaceconnector.spi.types.domain.DataAddress;
import org.eclipse.dataspaceconnector.spi.types.domain.transfer.DataRequest;
import org.eclipse.dataspaceconnector.spi.types.domain.transfer.ProvisionedDataDestinationResource;
import org.eclipse.dataspaceconnector.spi.types.domain.transfer.StatusCheckerRegistry;
import org.eclipse.dataspaceconnector.spi.types.domain.transfer.TransferProcess;
import org.eclipse.dataspaceconnector.spi.types.domain.transfer.TransferProcessStates;
import org.jetbrains.annotations.NotNull;

import java.time.Duration;
import java.util.Timer;
import java.util.TimerTask;

public class TransferSimulationExtension implements ServiceExtension {

    public static final String TEST_TYPE = "test-type";
    private static final long ALMOST_TEN_MINUTES = Duration.ofMinutes(9).plusSeconds(55).toMillis();
    @Inject
    private TransferProcessStore store;

    @Inject
    private StatusCheckerRegistry statusCheckerRegistry;

    @Override
    public void initialize(ServiceExtensionContext context) {
        statusCheckerRegistry.register(TEST_TYPE, (transferProcess, resources) -> false); //never completes
        //Insert a test TP after a delay to simulate a zombie transfer
        new Timer().schedule(
                new TimerTask() {
                    @Override
                    public void run() {
                        var tp = TransferProcess.Builder.newInstance()
                                .id("tp-sample-04.2")
                                .dataRequest(getRequest())
                                .state(TransferProcessStates.IN_PROGRESS.code())
                                .stateTimestamp(System.currentTimeMillis() - ALMOST_TEN_MINUTES)
                                .build();
                        tp.addProvisionedResource(createDummyResource());

                        context.getMonitor().info("Insert Dummy TransferProcess");
                        store.update(tp);
                    }
                },
                5000
        );


    }

    @NotNull
    private ProvisionedDataDestinationResource createDummyResource() {
        return new ProvisionedDataDestinationResource() {
            @Override
            public DataAddress createDataDestination() {
                return null;
            }

            @Override
            public String getResourceName() {
                return "test resource";
            }
        };
    }

    private DataRequest getRequest() {
        return DataRequest.Builder.newInstance()
                .id("sample-04.2-datarequest")
                .destinationType(TEST_TYPE)
                .managedResources(true)
                .build();
    }

}
