/*
 * Copyright (c) Microsoft Corporation.
 * All rights reserved.
 */

package org.eclipse.dataspaceconnector.spi.types.domain.transfer;

import com.fasterxml.jackson.annotation.JsonTypeName;
import org.eclipse.dataspaceconnector.spi.types.domain.Polymorphic;

import java.util.Map;

/**
 * A temporary token with write privileges to the data destination.
 */
@JsonTypeName("dataspaceconnector:secrettoken")
public interface SecretToken extends Polymorphic {

    long getExpiration();

    Map<String, ?> flatten();
}