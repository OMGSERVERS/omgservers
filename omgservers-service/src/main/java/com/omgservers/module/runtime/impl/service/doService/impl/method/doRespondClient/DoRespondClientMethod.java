package com.omgservers.module.runtime.impl.service.doService.impl.method.doRespondClient;

import com.omgservers.dto.runtime.DoRespondClientRequest;
import com.omgservers.dto.runtime.DoRespondClientResponse;
import io.smallrye.mutiny.Uni;

public interface DoRespondClientMethod {
    Uni<DoRespondClientResponse> doRespondClient(DoRespondClientRequest request);
}
