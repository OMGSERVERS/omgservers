package com.omgservers.service.module.root.impl.service.rootService;

import com.omgservers.model.dto.root.root.DeleteRootRequest;
import com.omgservers.model.dto.root.root.DeleteRootResponse;
import com.omgservers.model.dto.root.root.GetRootRequest;
import com.omgservers.model.dto.root.root.GetRootResponse;
import com.omgservers.model.dto.root.root.SyncRootRequest;
import com.omgservers.model.dto.root.root.SyncRootResponse;
import com.omgservers.model.dto.root.rootEntityRef.DeleteRootEntityRefRequest;
import com.omgservers.model.dto.root.rootEntityRef.DeleteRootEntityRefResponse;
import com.omgservers.model.dto.root.rootEntityRef.FindRootEntityRefRequest;
import com.omgservers.model.dto.root.rootEntityRef.FindRootEntityRefResponse;
import com.omgservers.model.dto.root.rootEntityRef.GetRootEntityRefRequest;
import com.omgservers.model.dto.root.rootEntityRef.GetRootEntityRefResponse;
import com.omgservers.model.dto.root.rootEntityRef.SyncRootEntityRefRequest;
import com.omgservers.model.dto.root.rootEntityRef.SyncRootEntityRefResponse;
import com.omgservers.model.dto.root.rootEntityRef.ViewRootEntityRefsRequest;
import com.omgservers.model.dto.root.rootEntityRef.ViewRootEntityRefsResponse;
import io.smallrye.mutiny.Uni;
import jakarta.validation.Valid;

public interface RootService {
    Uni<GetRootResponse> getRoot(@Valid GetRootRequest request);

    Uni<SyncRootResponse> syncRoot(@Valid SyncRootRequest request);

    Uni<SyncRootResponse> syncRootWithIdempotency(@Valid SyncRootRequest request);

    Uni<DeleteRootResponse> deleteRoot(@Valid DeleteRootRequest request);

    Uni<GetRootEntityRefResponse> getRootEntityRef(@Valid GetRootEntityRefRequest request);

    Uni<FindRootEntityRefResponse> findRootEntityRef(@Valid FindRootEntityRefRequest request);

    Uni<ViewRootEntityRefsResponse> viewRootEntityRefs(@Valid ViewRootEntityRefsRequest request);

    Uni<SyncRootEntityRefResponse> syncRootEntityRef(@Valid SyncRootEntityRefRequest request);

    Uni<SyncRootEntityRefResponse> syncRootEntityRefWithIdempotency(@Valid SyncRootEntityRefRequest request);

    Uni<DeleteRootEntityRefResponse> deleteRootEntityRef(@Valid DeleteRootEntityRefRequest request);
}
