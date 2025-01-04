package com.omgservers.service.module.alias.impl.service.webService.impl.api;

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
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

@Tag(name = "Alias Module API")
@Path("/service/v1/module/alias/request")
public interface AliasApi {

    @PUT
    @Path("/get-alias")
    Uni<GetAliasResponse> execute(GetAliasRequest request);

    @PUT
    @Path("/find-alias")
    Uni<FindAliasResponse> execute(FindAliasRequest request);

    @PUT
    @Path("/view-aliases")
    Uni<ViewAliasesResponse> execute(ViewAliasesRequest request);

    @PUT
    @Path("/sync-alias")
    Uni<SyncAliasResponse> execute(SyncAliasRequest request);

    @PUT
    @Path("/delete-alias")
    Uni<DeleteAliasResponse> execute(DeleteAliasRequest request);
}
