package com.omgservers.base.impl.service.indexHelpService.impl.method.deleteIndexMethod;

import com.omgservers.dto.internalModule.DeleteIndexHelpRequest;
import io.smallrye.mutiny.Uni;

public interface DeleteIndexMethod {
    Uni<Void> deleteIndex(DeleteIndexHelpRequest request);
}
