package com.omgservers.service.module.root.impl.service.rootService;

import com.omgservers.schema.module.root.root.DeleteRootRequest;
import com.omgservers.schema.module.root.root.DeleteRootResponse;
import com.omgservers.schema.module.root.root.GetRootRequest;
import com.omgservers.schema.module.root.root.GetRootResponse;
import com.omgservers.schema.module.root.root.SyncRootRequest;
import com.omgservers.schema.module.root.root.SyncRootResponse;
import com.omgservers.schema.module.root.rootEntityRef.DeleteRootEntityRefRequest;
import com.omgservers.schema.module.root.rootEntityRef.DeleteRootEntityRefResponse;
import com.omgservers.schema.module.root.rootEntityRef.FindRootEntityRefRequest;
import com.omgservers.schema.module.root.rootEntityRef.FindRootEntityRefResponse;
import com.omgservers.schema.module.root.rootEntityRef.GetRootEntityRefRequest;
import com.omgservers.schema.module.root.rootEntityRef.GetRootEntityRefResponse;
import com.omgservers.schema.module.root.rootEntityRef.SyncRootEntityRefRequest;
import com.omgservers.schema.module.root.rootEntityRef.SyncRootEntityRefResponse;
import com.omgservers.schema.module.root.rootEntityRef.ViewRootEntityRefsRequest;
import com.omgservers.schema.module.root.rootEntityRef.ViewRootEntityRefsResponse;
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
