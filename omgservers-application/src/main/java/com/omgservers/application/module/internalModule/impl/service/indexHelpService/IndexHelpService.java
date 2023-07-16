package com.omgservers.application.module.internalModule.impl.service.indexHelpService;

import com.omgservers.application.module.internalModule.impl.service.indexHelpService.request.DeleteIndexHelpRequest;
import com.omgservers.application.module.internalModule.impl.service.indexHelpService.request.GetIndexHelpRequest;
import com.omgservers.application.module.internalModule.impl.service.indexHelpService.request.SyncIndexHelpRequest;
import com.omgservers.application.module.internalModule.impl.service.indexHelpService.response.GetIndexHelpResponse;
import io.smallrye.mutiny.Uni;

public interface IndexHelpService {

    Uni<GetIndexHelpResponse> getIndex(GetIndexHelpRequest request);

    Uni<Void> syncIndex(SyncIndexHelpRequest request);

    Uni<Void> deleteIndex(DeleteIndexHelpRequest request);
}
