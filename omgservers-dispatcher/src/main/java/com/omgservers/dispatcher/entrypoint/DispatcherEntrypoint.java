package com.omgservers.dispatcher.entrypoint;

import com.omgservers.dispatcher.entrypoint.impl.service.entrypointService.EntrypointService;

public interface DispatcherEntrypoint {

    EntrypointService getEntrypointService();
}
