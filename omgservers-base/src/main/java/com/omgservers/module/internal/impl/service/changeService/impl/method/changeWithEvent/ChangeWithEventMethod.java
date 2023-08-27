package com.omgservers.module.internal.impl.service.changeService.impl.method.changeWithEvent;

import com.omgservers.dto.internal.ChangeWithEventRequest;
import com.omgservers.dto.internal.ChangeWithEventResponse;
import io.smallrye.mutiny.Uni;

public interface ChangeWithEventMethod {

    Uni<ChangeWithEventResponse> changeWithEvent(ChangeWithEventRequest request);
}
