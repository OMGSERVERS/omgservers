package com.omgservers.service.shard.root.impl.service.webService;

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

public interface WebService {

    Uni<GetRootResponse> execute(GetRootRequest request);

    Uni<SyncRootResponse> execute(SyncRootRequest request);

    Uni<DeleteRootResponse> execute(DeleteRootRequest request);

    Uni<GetRootEntityRefResponse> execute(GetRootEntityRefRequest request);

    Uni<FindRootEntityRefResponse> execute(FindRootEntityRefRequest request);

    Uni<ViewRootEntityRefsResponse> execute(ViewRootEntityRefsRequest request);

    Uni<SyncRootEntityRefResponse> execute(SyncRootEntityRefRequest request);

    Uni<DeleteRootEntityRefResponse> execute(DeleteRootEntityRefRequest request);
}
