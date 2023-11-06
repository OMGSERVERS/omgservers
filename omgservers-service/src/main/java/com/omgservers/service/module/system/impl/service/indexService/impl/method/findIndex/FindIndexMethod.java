package com.omgservers.service.module.system.impl.service.indexService.impl.method.findIndex;

import com.omgservers.model.dto.system.FindIndexRequest;
import com.omgservers.model.dto.system.FindIndexResponse;
import io.smallrye.mutiny.Uni;

public interface FindIndexMethod {
    Uni<FindIndexResponse> findIndex(FindIndexRequest request);
}
