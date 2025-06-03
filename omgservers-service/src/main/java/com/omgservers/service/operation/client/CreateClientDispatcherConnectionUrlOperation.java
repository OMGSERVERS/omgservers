package com.omgservers.service.operation.client;

import java.net.URI;

public interface CreateClientDispatcherConnectionUrlOperation {
    URI execute(Long clientId, Long runtimeId);
}
