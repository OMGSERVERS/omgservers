package com.omgservers.service.module.runtime.impl.service.doService.impl.method.doRespondClient;

import com.omgservers.model.dto.runtime.DoRespondClientRequest;
import com.omgservers.model.dto.runtime.DoRespondClientResponse;
import io.smallrye.mutiny.Uni;

public interface DoRespondClientMethod {
    Uni<DoRespondClientResponse> doRespondClient(DoRespondClientRequest request);
}
