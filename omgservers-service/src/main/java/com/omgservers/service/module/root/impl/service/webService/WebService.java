package com.omgservers.service.module.root.impl.service.webService;

import com.omgservers.model.dto.root.DeleteRootRequest;
import com.omgservers.model.dto.root.DeleteRootResponse;
import com.omgservers.model.dto.root.GetRootRequest;
import com.omgservers.model.dto.root.GetRootResponse;
import com.omgservers.model.dto.root.SyncRootRequest;
import com.omgservers.model.dto.root.SyncRootResponse;
import io.smallrye.mutiny.Uni;

public interface WebService {

    Uni<GetRootResponse> getRoot(GetRootRequest request);

    Uni<SyncRootResponse> syncRoot(SyncRootRequest request);

    Uni<DeleteRootResponse> deleteRoot(DeleteRootRequest request);
}
