package com.omgservers.module.internal.impl.service.changeService;

import com.omgservers.ChangeRequest;
import com.omgservers.ChangeWithEventRequest;
import com.omgservers.ChangeWithLogRequest;
import com.omgservers.ChangeResponse;
import com.omgservers.ChangeWithEventResponse;
import com.omgservers.ChangeWithLogResponse;
import io.smallrye.mutiny.Uni;

public interface ChangeService {

    Uni<ChangeResponse> change(ChangeRequest request);

    Uni<ChangeWithLogResponse> changeWithLog(ChangeWithLogRequest request);

    Uni<ChangeWithEventResponse> changeWithEvent(ChangeWithEventRequest request);
}
