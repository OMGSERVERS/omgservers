package com.omgservers.module.internal.impl.service.changeService.impl.method.changeWithEvent;

import com.omgservers.ChangeWithEventRequest;
import com.omgservers.ChangeWithEventResponse;
import io.smallrye.mutiny.Uni;

public interface ChangeWithEventMethod {

    Uni<ChangeWithEventResponse> changeWithEvent(ChangeWithEventRequest request);
}
