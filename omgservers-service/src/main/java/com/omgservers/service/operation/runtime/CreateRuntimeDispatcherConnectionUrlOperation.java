package com.omgservers.service.operation.runtime;

import java.net.URI;

public interface CreateRuntimeDispatcherConnectionUrlOperation {
    URI execute(Long userId, Long runtimeId);
}
