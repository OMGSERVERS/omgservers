package com.omgservers.base.module.internal.impl.service.changeService.impl.method.changeWithEvent;

import com.omgservers.dto.internalModule.ChangeWithEventRequest;
import com.omgservers.dto.internalModule.ChangeWithEventResponse;
import io.smallrye.mutiny.Uni;

public interface ChangeWithEventMethod {

    Uni<ChangeWithEventResponse> changeWithEvent(ChangeWithEventRequest request);
}
