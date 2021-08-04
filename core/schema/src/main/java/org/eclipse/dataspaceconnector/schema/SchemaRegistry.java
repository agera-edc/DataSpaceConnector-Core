/*
 * Copyright (c) Microsoft Corporation.
 * All rights reserved.
 */

package org.eclipse.dataspaceconnector.schema;

import java.util.Collection;

public interface SchemaRegistry {
    String FEATURE = "schema-registry";

    void register(Schema schema);

    Schema getSchema(String identifier);

    boolean hasSchema(String identifier);

    Collection<Schema> getSchemas();
}