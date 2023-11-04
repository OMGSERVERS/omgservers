package com.omgservers.service.module.system.impl.service.eventService.impl.method.updateEventsStatus;

import com.omgservers.model.dto.system.UpdateEventsStatusRequest;
import com.omgservers.model.dto.system.UpdateEventsStatusResponse;
import io.smallrye.mutiny.Uni;

public interface UpdateEventsStatusMethod {
    Uni<UpdateEventsStatusResponse> updateEventsStatus(UpdateEventsStatusRequest request);
}
