package com.omgservers.service.module.system;

import com.omgservers.service.module.system.impl.service.bootstrapService.BootstrapService;
import com.omgservers.service.module.system.impl.service.eventService.EventService;
import com.omgservers.service.module.system.impl.service.indexService.IndexService;
import com.omgservers.service.module.system.impl.service.serviceAccountService.ServiceAccountService;

public interface SystemModule {

    ServiceAccountService getServiceAccountService();

    BootstrapService getBootstrapService();

    EventService getEventService();

    IndexService getIndexService();
}