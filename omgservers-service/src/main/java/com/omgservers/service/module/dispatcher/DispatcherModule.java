package com.omgservers.service.module.dispatcher;

import com.omgservers.service.module.dispatcher.impl.service.dispatcherService.DispatcherService;
import com.omgservers.service.module.dispatcher.impl.service.roomService.RoomService;
import com.omgservers.service.module.dispatcher.impl.service.routerService.RouterService;

public interface DispatcherModule {

    DispatcherService getDispatcherService();

    RouterService getRouterService();

    RoomService getRoomService();
}
