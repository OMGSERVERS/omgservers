package com.omgservers.service.module.dispatcher.impl;

import com.omgservers.service.module.dispatcher.DispatcherModule;
import com.omgservers.service.module.dispatcher.impl.service.dispatcherService.DispatcherService;
import com.omgservers.service.module.dispatcher.impl.service.roomService.RoomService;
import com.omgservers.service.module.dispatcher.impl.service.routerService.RouterService;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
class DispatcherModuleImpl implements DispatcherModule {

    final DispatcherService dispatcherService;

    final RouterService routerService;

    final RoomService roomService;
}
