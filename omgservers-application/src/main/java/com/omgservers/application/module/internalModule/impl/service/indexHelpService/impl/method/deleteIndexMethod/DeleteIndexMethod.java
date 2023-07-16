package com.omgservers.application.module.internalModule.impl.service.indexHelpService.impl.method.deleteIndexMethod;

import com.omgservers.application.module.internalModule.impl.service.indexHelpService.request.DeleteIndexHelpRequest;
import io.smallrye.mutiny.Uni;

public interface DeleteIndexMethod {
    Uni<Void> deleteIndex(DeleteIndexHelpRequest request);
}
