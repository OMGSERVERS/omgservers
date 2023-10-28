package com.omgservers.module.system.impl.service.indexService;

import com.omgservers.model.dto.internal.DeleteIndexRequest;
import com.omgservers.model.dto.internal.GetIndexRequest;
import com.omgservers.model.dto.internal.SyncIndexRequest;
import com.omgservers.model.dto.internal.GetIndexResponse;
import io.smallrye.mutiny.Uni;

public interface IndexService {

    Uni<GetIndexResponse> getIndex(GetIndexRequest request);

    Uni<Void> syncIndex(SyncIndexRequest request);

    Uni<Void> deleteIndex(DeleteIndexRequest request);
}
