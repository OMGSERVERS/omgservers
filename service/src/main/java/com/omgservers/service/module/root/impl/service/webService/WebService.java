package com.omgservers.service.module.root.impl.service.webService;

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
