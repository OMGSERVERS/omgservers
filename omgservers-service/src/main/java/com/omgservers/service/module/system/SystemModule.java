package com.omgservers.service.module.system;

import com.omgservers.service.module.system.impl.service.containerService.ContainerService;
import com.omgservers.service.module.system.impl.service.eventService.EventService;
import com.omgservers.service.module.system.impl.service.handlerService.HandlerService;
import com.omgservers.service.module.system.impl.service.indexService.IndexService;
import com.omgservers.service.module.system.impl.service.jobService.JobService;
import com.omgservers.service.module.system.impl.service.logService.LogService;
import com.omgservers.service.module.system.impl.service.serviceAccountService.ServiceAccountService;
import com.omgservers.service.module.system.impl.service.shortcutService.ShortcutService;

public interface SystemModule {

    ServiceAccountService getServiceAccountService();

    ContainerService getContainerService();

    ShortcutService getShortcutService();

    HandlerService getHandlerService();

    EventService getEventService();

    IndexService getIndexService();

    JobService getJobService();

    LogService getLogService();

}