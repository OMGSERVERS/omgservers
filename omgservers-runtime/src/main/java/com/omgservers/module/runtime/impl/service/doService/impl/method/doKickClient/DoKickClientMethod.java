package com.omgservers.module.runtime.impl.service.doService.impl.method.doKickClient;

import com.omgservers.dto.runtime.DoKickClientRequest;
import com.omgservers.dto.runtime.DoKickClientResponse;
import io.smallrye.mutiny.Uni;

public interface DoKickClientMethod {
    Uni<DoKickClientResponse> doKickClient(DoKickClientRequest request);
}
