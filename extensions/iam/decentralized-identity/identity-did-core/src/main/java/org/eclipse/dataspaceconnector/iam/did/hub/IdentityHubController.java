/*
 *  Copyright (c) 2021 Microsoft Corporation
 *
 *  This program and the accompanying materials are made available under the
 *  terms of the Apache License, Version 2.0 which is available at
 *  https://www.apache.org/licenses/LICENSE-2.0
 *
 *  SPDX-License-Identifier: Apache-2.0
 *
 *  Contributors:
 *       Microsoft Corporation - initial API and implementation
 *
 */
package org.eclipse.dataspaceconnector.iam.did.hub;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.dataspaceconnector.iam.did.spi.hub.IdentityHub;
import org.eclipse.dataspaceconnector.iam.did.spi.hub.message.Commit;

import java.util.Map;
import java.util.UUID;

/**
 * Binds the identity hub to an HTTP REST endpoint.
 */
@Consumes({ MediaType.APPLICATION_JSON })
@Produces({ MediaType.APPLICATION_JSON })
@Path("/identity-hub")
public class IdentityHubController {
    private final IdentityHub hub;

    public IdentityHubController(IdentityHub hub) {
        this.hub = hub;
    }

    @POST
    @Path("collections-commit")
    public Response writeCommit(Map<String, String> credential) {
        var objectId = UUID.randomUUID().toString();
        var commit = Commit.Builder.newInstance().type("RegistrationCredentials").context("GAIA-X").iss("test").sub("test").objectId(objectId).payload(credential).build();
        hub.write(commit);
        return Response.ok().build();
    }

    @POST
    @Path("collections")
    public String write(String jwe) {
        return hub.write(jwe);
    }

    @POST
    @Path("query-commits")
    public String queryCommits(String jwe) {
        return hub.queryCommits(jwe);
    }

    @POST
    @Path("query-objects")
    public String queryObjects(String jwe) {
        return hub.queryObjects(jwe);
    }
}
