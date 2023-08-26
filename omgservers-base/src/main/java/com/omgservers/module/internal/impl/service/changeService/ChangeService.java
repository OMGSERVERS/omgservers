package com.omgservers.module.internal.impl.service.changeService;

import com.omgservers.dto.internalModule.ChangeRequest;
import com.omgservers.dto.internalModule.ChangeWithEventRequest;
import com.omgservers.dto.internalModule.ChangeWithLogRequest;
import com.omgservers.dto.internalModule.ChangeResponse;
import com.omgservers.dto.internalModule.ChangeWithEventResponse;
import com.omgservers.dto.internalModule.ChangeWithLogResponse;
import io.smallrye.mutiny.Uni;

public interface ChangeService {

    Uni<ChangeResponse> change(ChangeRequest request);

    Uni<ChangeWithLogResponse> changeWithLog(ChangeWithLogRequest request);

    Uni<ChangeWithEventResponse> changeWithEvent(ChangeWithEventRequest request);
}
