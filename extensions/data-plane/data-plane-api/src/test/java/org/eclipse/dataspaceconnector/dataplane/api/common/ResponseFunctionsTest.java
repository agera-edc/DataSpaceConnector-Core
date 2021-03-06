/*
 *  Copyright (c) 2022 Microsoft Corporation
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
package org.eclipse.dataspaceconnector.dataplane.api.common;

import org.junit.jupiter.api.Test;

import java.util.List;

import static jakarta.ws.rs.core.Response.Status.BAD_REQUEST;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.InstanceOfAssertFactories.MAP;
import static org.eclipse.dataspaceconnector.dataplane.api.common.ResponseFunctions.validationErrors;

class ResponseFunctionsTest {

    @Test
    void verifyValidationErrors() {
        var response = validationErrors(List.of("error1", "error2"));
        assertThat(response.getStatusInfo()).isEqualTo(BAD_REQUEST);
        assertThat(response.getEntity()).asInstanceOf(MAP).containsKey("errors");
    }
}
