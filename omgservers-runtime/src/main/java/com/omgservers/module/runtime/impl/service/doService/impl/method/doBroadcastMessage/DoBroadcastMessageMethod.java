package com.omgservers.module.runtime.impl.service.doService.impl.method.doBroadcastMessage;

import com.omgservers.dto.runtime.DoBroadcastMessageRequest;
import com.omgservers.dto.runtime.DoBroadcastMessageResponse;
import io.smallrye.mutiny.Uni;

public interface DoBroadcastMessageMethod {
    Uni<DoBroadcastMessageResponse> doBroadcastMessage(DoBroadcastMessageRequest request);
}
