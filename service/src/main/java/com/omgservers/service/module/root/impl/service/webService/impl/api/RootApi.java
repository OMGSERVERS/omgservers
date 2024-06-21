package com.omgservers.service.module.root.impl.service.webService.impl.api;

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
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

@Tag(name = "Root Module API")
@Path("/omgservers/v1/module/root/request")
public interface RootApi {

    @PUT
    @Path("/get-root")
    Uni<GetRootResponse> getRoot(GetRootRequest request);

    @PUT
    @Path("/sync-root")
    Uni<SyncRootResponse> syncRoot(SyncRootRequest request);

    @PUT
    @Path("/delete-root")
    Uni<DeleteRootResponse> deleteRoot(DeleteRootRequest request);

    @PUT
    @Path("/get-root-entity-ref")
    Uni<GetRootEntityRefResponse> getRootEntityRef(GetRootEntityRefRequest request);

    @PUT
    @Path("/find-root-entity-ref")
    Uni<FindRootEntityRefResponse> findRootEntityRef(FindRootEntityRefRequest request);

    @PUT
    @Path("/view-root-entity-refs")
    Uni<ViewRootEntityRefsResponse> viewRootEntityRefs(ViewRootEntityRefsRequest request);

    @PUT
    @Path("/sync-root-entity-ref")
    Uni<SyncRootEntityRefResponse> syncRootEntityRef(SyncRootEntityRefRequest request);

    @PUT
    @Path("/delete-root-entity-ref")
    Uni<DeleteRootEntityRefResponse> deleteRootEntityRef(DeleteRootEntityRefRequest request);
}
