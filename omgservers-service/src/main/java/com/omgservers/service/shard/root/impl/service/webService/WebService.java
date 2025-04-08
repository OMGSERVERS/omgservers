package com.omgservers.service.shard.root.impl.service.webService;

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
