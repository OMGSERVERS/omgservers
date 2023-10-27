package com.omgservers.module.runtime.impl.service.doService.impl.method.doUnicastMessage;

import com.omgservers.dto.runtime.DoUnicastMessageRequest;
import com.omgservers.dto.runtime.DoUnicastMessageResponse;
import io.smallrye.mutiny.Uni;

public interface DoUnicastMessageMethod {
    Uni<DoUnicastMessageResponse> doUnicastMessage(DoUnicastMessageRequest request);
}
