package com.omgservers.module.system.impl.service.eventService.impl.method.syncEvent;

import com.omgservers.model.dto.internal.SyncEventRequest;
import com.omgservers.model.dto.internal.SyncEventResponse;
import io.smallrye.mutiny.Uni;

public interface SyncEventMethod {
    Uni<SyncEventResponse> syncEvent(SyncEventRequest request);
}
