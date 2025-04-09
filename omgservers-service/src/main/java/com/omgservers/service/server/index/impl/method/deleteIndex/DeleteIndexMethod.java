package com.omgservers.service.server.index.impl.method.deleteIndex;

import com.omgservers.service.server.index.dto.DeleteIndexRequest;
import com.omgservers.service.server.index.dto.DeleteIndexResponse;
import io.smallrye.mutiny.Uni;

public interface DeleteIndexMethod {
    Uni<DeleteIndexResponse> deleteIndex(DeleteIndexRequest request);
}
