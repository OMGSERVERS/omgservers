package com.omgservers.service.service.index.impl.method.deleteIndex;

import com.omgservers.service.service.index.dto.DeleteIndexRequest;
import com.omgservers.service.service.index.dto.DeleteIndexResponse;
import io.smallrye.mutiny.Uni;

public interface DeleteIndexMethod {
    Uni<DeleteIndexResponse> deleteIndex(DeleteIndexRequest request);
}
