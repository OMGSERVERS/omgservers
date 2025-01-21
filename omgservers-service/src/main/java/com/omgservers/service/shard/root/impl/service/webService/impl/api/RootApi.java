package com.omgservers.service.shard.root.impl.service.webService.impl.api;

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
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

@Tag(name = "Root Shard API")
@Path("/service/v1/shard/root/request")
public interface RootApi {

    @POST
    @Path("/get-root")
    Uni<GetRootResponse> getRoot(GetRootRequest request);

    @POST
    @Path("/sync-root")
    Uni<SyncRootResponse> syncRoot(SyncRootRequest request);

    @POST
    @Path("/delete-root")
    Uni<DeleteRootResponse> deleteRoot(DeleteRootRequest request);

    @POST
    @Path("/get-root-entity-ref")
    Uni<GetRootEntityRefResponse> getRootEntityRef(GetRootEntityRefRequest request);

    @POST
    @Path("/find-root-entity-ref")
    Uni<FindRootEntityRefResponse> findRootEntityRef(FindRootEntityRefRequest request);

    @POST
    @Path("/view-root-entity-refs")
    Uni<ViewRootEntityRefsResponse> viewRootEntityRefs(ViewRootEntityRefsRequest request);

    @POST
    @Path("/sync-root-entity-ref")
    Uni<SyncRootEntityRefResponse> syncRootEntityRef(SyncRootEntityRefRequest request);

    @POST
    @Path("/delete-root-entity-ref")
    Uni<DeleteRootEntityRefResponse> deleteRootEntityRef(DeleteRootEntityRefRequest request);
}
