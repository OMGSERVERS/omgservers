package com.omgservers.module.system.impl.service.indexService.impl.method.deleteIndex;

import com.omgservers.model.dto.system.DeleteIndexRequest;
import io.smallrye.mutiny.Uni;

public interface DeleteIndexMethod {
    Uni<Void> deleteIndex(DeleteIndexRequest request);
}
