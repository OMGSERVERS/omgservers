package com.omgservers.service.module.runtime.impl.service.doService.impl.method.doKickClient;

import com.omgservers.model.dto.runtime.DoKickClientRequest;
import com.omgservers.model.dto.runtime.DoKickClientResponse;
import io.smallrye.mutiny.Uni;

public interface DoKickClientMethod {
    Uni<DoKickClientResponse> doKickClient(DoKickClientRequest request);
}
