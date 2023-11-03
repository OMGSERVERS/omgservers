package com.omgservers.module.system.impl.service.indexService;

import com.omgservers.model.dto.system.DeleteIndexRequest;
import com.omgservers.model.dto.system.GetIndexRequest;
import com.omgservers.model.dto.system.SyncIndexRequest;
import com.omgservers.model.dto.system.GetIndexResponse;
import io.smallrye.mutiny.Uni;

public interface IndexService {

    Uni<GetIndexResponse> getIndex(GetIndexRequest request);

    Uni<Void> syncIndex(SyncIndexRequest request);

    Uni<Void> deleteIndex(DeleteIndexRequest request);
}
