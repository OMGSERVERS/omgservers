package com.omgservers.module.runtime.impl.service.doService.impl.method.doMulticastMessage;

import com.omgservers.dto.runtime.DoMulticastMessageRequest;
import com.omgservers.dto.runtime.DoMulticastMessageResponse;
import io.smallrye.mutiny.Uni;

public interface DoMulticastMessageMethod {
    Uni<DoMulticastMessageResponse> doMulticastMessage(DoMulticastMessageRequest request);
}
