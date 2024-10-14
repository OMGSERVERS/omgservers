package com.omgservers.service.entrypoint.dispatcher;

import com.omgservers.service.entrypoint.dispatcher.impl.service.dispatcherService.DispatcherService;

public interface DispatcherEntrypoint {

    DispatcherService getService();
}
