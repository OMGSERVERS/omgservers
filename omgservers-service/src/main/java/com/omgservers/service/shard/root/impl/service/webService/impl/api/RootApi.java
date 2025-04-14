package com.omgservers.service.shard.root.impl.service.webService.impl.api;

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
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

@Tag(name = "Root Shard API")
@Path("/service/v1/shard/root/request")
public interface RootApi {

    @POST
    @Path("/get-root")
    Uni<GetRootResponse> execute(GetRootRequest request);

    @POST
    @Path("/sync-root")
    Uni<SyncRootResponse> execute(SyncRootRequest request);

    @POST
    @Path("/delete-root")
    Uni<DeleteRootResponse> execute(DeleteRootRequest request);

    @POST
    @Path("/get-root-entity-ref")
    Uni<GetRootEntityRefResponse> execute(GetRootEntityRefRequest request);

    @POST
    @Path("/find-root-entity-ref")
    Uni<FindRootEntityRefResponse> execute(FindRootEntityRefRequest request);

    @POST
    @Path("/view-root-entity-refs")
    Uni<ViewRootEntityRefsResponse> execute(ViewRootEntityRefsRequest request);

    @POST
    @Path("/sync-root-entity-ref")
    Uni<SyncRootEntityRefResponse> execute(SyncRootEntityRefRequest request);

    @POST
    @Path("/delete-root-entity-ref")
    Uni<DeleteRootEntityRefResponse> execute(DeleteRootEntityRefRequest request);
}
