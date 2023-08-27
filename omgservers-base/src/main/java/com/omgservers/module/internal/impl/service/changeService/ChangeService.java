package com.omgservers.module.internal.impl.service.changeService;

import com.omgservers.dto.internal.ChangeRequest;
import com.omgservers.dto.internal.ChangeWithEventRequest;
import com.omgservers.dto.internal.ChangeWithLogRequest;
import com.omgservers.dto.internal.ChangeResponse;
import com.omgservers.dto.internal.ChangeWithEventResponse;
import com.omgservers.dto.internal.ChangeWithLogResponse;
import io.smallrye.mutiny.Uni;

public interface ChangeService {

    Uni<ChangeResponse> change(ChangeRequest request);

    Uni<ChangeWithLogResponse> changeWithLog(ChangeWithLogRequest request);

    Uni<ChangeWithEventResponse> changeWithEvent(ChangeWithEventRequest request);
}
