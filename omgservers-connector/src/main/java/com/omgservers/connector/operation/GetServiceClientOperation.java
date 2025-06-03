package com.omgservers.connector.operation;

import java.net.URI;

public interface GetServiceClientOperation {
    ServiceClient execute(URI uri);
}
