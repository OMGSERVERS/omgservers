package com.omgservers.base.impl.service.indexHelpService;

import com.omgservers.dto.internalModule.DeleteIndexHelpRequest;
import com.omgservers.dto.internalModule.GetIndexHelpRequest;
import com.omgservers.dto.internalModule.SyncIndexHelpRequest;
import com.omgservers.dto.internalModule.GetIndexHelpResponse;
import io.smallrye.mutiny.Uni;

public interface IndexHelpService {

    Uni<GetIndexHelpResponse> getIndex(GetIndexHelpRequest request);

    Uni<Void> syncIndex(SyncIndexHelpRequest request);

    Uni<Void> deleteIndex(DeleteIndexHelpRequest request);
}
