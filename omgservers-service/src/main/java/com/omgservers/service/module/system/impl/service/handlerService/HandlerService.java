package com.omgservers.service.module.system.impl.service.handlerService;

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
import io.smallrye.mutiny.Uni;

public interface HandlerService {

    Uni<GetHandlerResponse> getHandler(GetHandlerRequest request);

    Uni<ViewHandlersResponse> viewHandlers(ViewHandlersRequest request);

    Uni<SyncHandlerResponse> syncHandler(SyncHandlerRequest request);

    Uni<DeleteHandlerResponse> deleteHandler(DeleteHandlerRequest request);

    Uni<HandleEventsResponse> handleEvents(HandleEventsRequest request);
}
