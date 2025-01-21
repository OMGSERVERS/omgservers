package com.omgservers.service.shard.alias.impl.service.webService.impl.api;

import com.omgservers.schema.module.alias.DeleteAliasRequest;
import com.omgservers.schema.module.alias.DeleteAliasResponse;
import com.omgservers.schema.module.alias.FindAliasRequest;
import com.omgservers.schema.module.alias.FindAliasResponse;
import com.omgservers.schema.module.alias.GetAliasRequest;
import com.omgservers.schema.module.alias.GetAliasResponse;
import com.omgservers.schema.module.alias.SyncAliasRequest;
import com.omgservers.schema.module.alias.SyncAliasResponse;
import com.omgservers.schema.module.alias.ViewAliasesRequest;
import com.omgservers.schema.module.alias.ViewAliasesResponse;
import io.smallrye.mutiny.Uni;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

@Tag(name = "Alias Shard API")
@Path("/service/v1/shard/alias/request")
public interface AliasApi {

    @POST
    @Path("/get-alias")
    Uni<GetAliasResponse> execute(GetAliasRequest request);

    @POST
    @Path("/find-alias")
    Uni<FindAliasResponse> execute(FindAliasRequest request);

    @POST
    @Path("/view-aliases")
    Uni<ViewAliasesResponse> execute(ViewAliasesRequest request);

    @POST
    @Path("/sync-alias")
    Uni<SyncAliasResponse> execute(SyncAliasRequest request);

    @POST
    @Path("/delete-alias")
    Uni<DeleteAliasResponse> execute(DeleteAliasRequest request);
}
