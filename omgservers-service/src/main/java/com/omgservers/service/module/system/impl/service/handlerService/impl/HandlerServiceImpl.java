package com.omgservers.service.module.system.impl.service.handlerService.impl;

import com.omgservers.model.dto.system.DeleteHandlerRequest;
import com.omgservers.model.dto.system.DeleteHandlerResponse;
import com.omgservers.model.dto.system.GetHandlerRequest;
import com.omgservers.model.dto.system.GetHandlerResponse;
import com.omgservers.model.dto.system.HandleEventsRequest;
import com.omgservers.model.dto.system.HandleEventsResponse;
import com.omgservers.model.dto.system.SyncHandlerRequest;
import com.omgservers.model.dto.system.SyncHandlerResponse;
import com.omgservers.model.dto.system.ViewHandlersRequest;
import com.omgservers.model.dto.system.ViewHandlersResponse;
import com.omgservers.service.module.system.impl.service.handlerService.HandlerService;
import com.omgservers.service.module.system.impl.service.handlerService.impl.method.deleteHandler.DeleteHandlerMethod;
import com.omgservers.service.module.system.impl.service.handlerService.impl.method.getHandler.GetHandlerMethod;
import com.omgservers.service.module.system.impl.service.handlerService.impl.method.handleEvents.HandleEventsMethod;
import com.omgservers.service.module.system.impl.service.handlerService.impl.method.syncHandler.SyncHandlerMethod;
import com.omgservers.service.module.system.impl.service.handlerService.impl.method.viewHandler.ViewHandlersMethod;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
class HandlerServiceImpl implements HandlerService {

    final DeleteHandlerMethod deleteHandlerMethod;
    final ViewHandlersMethod viewHandlersMethod;
    final HandleEventsMethod handleEventsMethod;
    final SyncHandlerMethod syncHandlerMethod;
    final GetHandlerMethod getHandlerMethod;

    @Override
    public Uni<GetHandlerResponse> getHandler(final GetHandlerRequest request) {
        return getHandlerMethod.getHandler(request);
    }

    @Override
    public Uni<ViewHandlersResponse> viewHandlers(final ViewHandlersRequest request) {
        return viewHandlersMethod.viewHandlers(request);
    }

    @Override
    public Uni<SyncHandlerResponse> syncHandler(final SyncHandlerRequest request) {
        return syncHandlerMethod.syncHandler(request);
    }

    @Override
    public Uni<DeleteHandlerResponse> deleteHandler(final DeleteHandlerRequest request) {
        return deleteHandlerMethod.deleteHandler(request);
    }

    @Override
    public Uni<HandleEventsResponse> handleEvents(final HandleEventsRequest request) {
        return handleEventsMethod.handleEvents(request);
    }
}
