package com.omgservers.module.internal.impl.service.indexService.impl.method.deleteIndex;

import com.omgservers.dto.internal.DeleteIndexRequest;
import io.smallrye.mutiny.Uni;

public interface DeleteIndexMethod {
    Uni<Void> deleteIndex(DeleteIndexRequest request);
}
