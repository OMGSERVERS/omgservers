package com.omgservers.module.internal.impl.service.changeService.impl.method.changeWithLog;

import com.omgservers.ChangeWithLogRequest;
import com.omgservers.ChangeWithLogResponse;
import io.smallrye.mutiny.Uni;

public interface ChangeWithLogMethod {

    Uni<ChangeWithLogResponse> changeWithLog(ChangeWithLogRequest request);
}
