package org.eclipse.dataspaceconnector.catalog.spi.model;


import org.eclipse.dataspaceconnector.catalog.spi.NodeQueryAdapter;

/**
 * {@link NodeQueryAdapter}s accept {@code UpdateRequests} to send out catalog queries
 */
public class UpdateRequest {
    private final String nodeUrl;

    public UpdateRequest(String nodeUrl) {
        this.nodeUrl = nodeUrl;
    }

    public String getNodeUrl() {
        return nodeUrl;
    }
}
