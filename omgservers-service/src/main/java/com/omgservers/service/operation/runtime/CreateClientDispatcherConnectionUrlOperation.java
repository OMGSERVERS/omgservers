package com.omgservers.service.operation.runtime;

import java.net.URI;

public interface CreateClientDispatcherConnectionUrlOperation {
    URI execute(Long clientId, Long runtimeId);
}
