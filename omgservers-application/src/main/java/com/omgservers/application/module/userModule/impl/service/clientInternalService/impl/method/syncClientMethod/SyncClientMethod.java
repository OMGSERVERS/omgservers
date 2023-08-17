package com.omgservers.application.module.userModule.impl.service.clientInternalService.impl.method.syncClientMethod;

import com.omgservers.application.module.userModule.impl.service.clientInternalService.request.SyncClientInternalRequest;
import com.omgservers.application.module.userModule.impl.service.clientInternalService.response.SyncClientInternalResponse;
import io.smallrye.mutiny.Uni;

public interface SyncClientMethod {

    Uni<SyncClientInternalResponse> syncClient(SyncClientInternalRequest request);
}
