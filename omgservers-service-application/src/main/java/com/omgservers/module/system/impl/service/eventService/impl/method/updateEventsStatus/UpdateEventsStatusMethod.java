package com.omgservers.module.system.impl.service.eventService.impl.method.updateEventsStatus;

import com.omgservers.model.dto.internal.UpdateEventsStatusRequest;
import com.omgservers.model.dto.internal.UpdateEventsStatusResponse;
import io.smallrye.mutiny.Uni;

public interface UpdateEventsStatusMethod {
    Uni<UpdateEventsStatusResponse> updateEventsStatus(UpdateEventsStatusRequest request);
}
