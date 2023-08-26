package com.omgservers.module.internal.impl.service.indexService;

import com.omgservers.dto.internalModule.DeleteIndexRequest;
import com.omgservers.dto.internalModule.GetIndexRequest;
import com.omgservers.dto.internalModule.SyncIndexRequest;
import com.omgservers.dto.internalModule.GetIndexHelpResponse;
import io.smallrye.mutiny.Uni;

public interface IndexService {

    Uni<GetIndexHelpResponse> getIndex(GetIndexRequest request);

    Uni<Void> syncIndex(SyncIndexRequest request);

    Uni<Void> deleteIndex(DeleteIndexRequest request);
}
