package com.omgservers.service.module.system.impl.service.eventService.impl.method.syncEvent;

import com.omgservers.schema.service.system.SyncEventRequest;
import com.omgservers.schema.service.system.SyncEventResponse;
import io.smallrye.mutiny.Uni;

public interface SyncEventMethod {
    Uni<SyncEventResponse> syncEvent(SyncEventRequest request);
}
