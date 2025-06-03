package com.omgservers.service.operation.client;

import java.net.URI;

public interface CreateClientConnectorUrlOperation {
    URI execute(Long clientId);
}
