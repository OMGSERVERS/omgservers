package com.omgservers.service.module.root.impl.service.rootService;

import com.omgservers.model.dto.root.DeleteRootRequest;
import com.omgservers.model.dto.root.DeleteRootResponse;
import com.omgservers.model.dto.root.GetRootRequest;
import com.omgservers.model.dto.root.GetRootResponse;
import com.omgservers.model.dto.root.SyncRootRequest;
import com.omgservers.model.dto.root.SyncRootResponse;
import io.smallrye.mutiny.Uni;
import jakarta.validation.Valid;

public interface RootService {
    Uni<GetRootResponse> getRoot(@Valid GetRootRequest request);

    Uni<SyncRootResponse> syncRoot(@Valid SyncRootRequest request);

    Uni<DeleteRootResponse> deleteRoot(@Valid DeleteRootRequest request);
}
