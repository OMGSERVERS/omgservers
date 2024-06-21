package com.omgservers.service.module.system.impl.service.indexService.impl.method.deleteIndex;

import com.omgservers.model.dto.system.DeleteIndexRequest;
import com.omgservers.model.dto.system.DeleteIndexResponse;
import io.smallrye.mutiny.Uni;

public interface DeleteIndexMethod {
    Uni<DeleteIndexResponse> deleteIndex(DeleteIndexRequest request);
}
