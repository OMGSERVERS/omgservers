package com.omgservers.service.module.runtime.impl.service.doService.impl.method.doMulticastMessage;

import com.omgservers.model.dto.runtime.DoMulticastMessageRequest;
import com.omgservers.model.dto.runtime.DoMulticastMessageResponse;
import io.smallrye.mutiny.Uni;

public interface DoMulticastMessageMethod {
    Uni<DoMulticastMessageResponse> doMulticastMessage(DoMulticastMessageRequest request);
}
