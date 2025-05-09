package com.omgservers.service.shard.root.impl.service.rootService;

import com.omgservers.schema.shard.root.root.DeleteRootRequest;
import com.omgservers.schema.shard.root.root.DeleteRootResponse;
import com.omgservers.schema.shard.root.root.GetRootRequest;
import com.omgservers.schema.shard.root.root.GetRootResponse;
import com.omgservers.schema.shard.root.root.SyncRootRequest;
import com.omgservers.schema.shard.root.root.SyncRootResponse;
import com.omgservers.schema.shard.root.rootEntityRef.DeleteRootEntityRefRequest;
import com.omgservers.schema.shard.root.rootEntityRef.DeleteRootEntityRefResponse;
import com.omgservers.schema.shard.root.rootEntityRef.FindRootEntityRefRequest;
import com.omgservers.schema.shard.root.rootEntityRef.FindRootEntityRefResponse;
import com.omgservers.schema.shard.root.rootEntityRef.GetRootEntityRefRequest;
import com.omgservers.schema.shard.root.rootEntityRef.GetRootEntityRefResponse;
import com.omgservers.schema.shard.root.rootEntityRef.SyncRootEntityRefRequest;
import com.omgservers.schema.shard.root.rootEntityRef.SyncRootEntityRefResponse;
import com.omgservers.schema.shard.root.rootEntityRef.ViewRootEntityRefsRequest;
import com.omgservers.schema.shard.root.rootEntityRef.ViewRootEntityRefsResponse;
import io.smallrye.mutiny.Uni;
import jakarta.validation.Valid;

public interface RootService {

    Uni<GetRootResponse> execute(@Valid GetRootRequest request);

    Uni<SyncRootResponse> execute(@Valid SyncRootRequest request);

    Uni<SyncRootResponse> executeWithIdempotency(@Valid SyncRootRequest request);

    Uni<DeleteRootResponse> execute(@Valid DeleteRootRequest request);

    Uni<GetRootEntityRefResponse> execute(@Valid GetRootEntityRefRequest request);

    Uni<FindRootEntityRefResponse> execute(@Valid FindRootEntityRefRequest request);

    Uni<ViewRootEntityRefsResponse> execute(@Valid ViewRootEntityRefsRequest request);

    Uni<SyncRootEntityRefResponse> execute(@Valid SyncRootEntityRefRequest request);

    Uni<SyncRootEntityRefResponse> executeWithIdempotency(@Valid SyncRootEntityRefRequest request);

    Uni<DeleteRootEntityRefResponse> execute(@Valid DeleteRootEntityRefRequest request);
}
