package com.omgservers.dispatcher.module;

import com.omgservers.dispatcher.module.impl.service.dispatcherService.DispatcherService;

public interface DispatcherModule {

    DispatcherService getDispatcherService();
}
