package com.omgservers.service.server.service.index.impl.method.deleteIndex;

import com.omgservers.schema.service.system.DeleteIndexRequest;
import com.omgservers.schema.service.system.DeleteIndexResponse;
import io.smallrye.mutiny.Uni;

public interface DeleteIndexMethod {
    Uni<DeleteIndexResponse> deleteIndex(DeleteIndexRequest request);
}
