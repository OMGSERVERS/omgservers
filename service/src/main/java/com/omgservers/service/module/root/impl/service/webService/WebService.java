package com.omgservers.service.module.root.impl.service.webService;

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

    Uni<GetRootResponse> getRoot(GetRootRequest request);

    Uni<SyncRootResponse> syncRoot(SyncRootRequest request);

    Uni<DeleteRootResponse> deleteRoot(DeleteRootRequest request);

    Uni<GetRootEntityRefResponse> getRootEntityRef(GetRootEntityRefRequest request);

    Uni<FindRootEntityRefResponse> findRootEntityRef(FindRootEntityRefRequest request);

    Uni<ViewRootEntityRefsResponse> viewRootEntityRefs(ViewRootEntityRefsRequest request);

    Uni<SyncRootEntityRefResponse> syncRootEntityRef(SyncRootEntityRefRequest request);

    Uni<DeleteRootEntityRefResponse> deleteRootEntityRef(DeleteRootEntityRefRequest request);
}
