package com.omgservers.service.server.event.impl.method;

import com.omgservers.service.server.event.dto.SyncEventRequest;
import com.omgservers.service.server.event.dto.SyncEventResponse;
import io.smallrye.mutiny.Uni;

public interface SyncEventMethod {
    Uni<SyncEventResponse> syncEvent(SyncEventRequest request);
}
