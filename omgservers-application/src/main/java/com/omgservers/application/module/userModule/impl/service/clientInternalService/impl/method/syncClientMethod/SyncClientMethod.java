package com.omgservers.application.module.userModule.impl.service.clientInternalService.impl.method.syncClientMethod;

import com.omgservers.dto.userModule.SyncClientInternalRequest;
import com.omgservers.dto.userModule.SyncClientInternalResponse;
import io.smallrye.mutiny.Uni;

public interface SyncClientMethod {

    Uni<SyncClientInternalResponse> syncClient(SyncClientInternalRequest request);
}
