package com.omgservers.service.master.entity.impl.service.webService.impl.api;

import com.omgservers.schema.master.entity.DeleteEntityRequest;
import com.omgservers.schema.master.entity.DeleteEntityResponse;
import com.omgservers.schema.master.entity.FindEntityRequest;
import com.omgservers.schema.master.entity.FindEntityResponse;
import com.omgservers.schema.master.entity.GetEntityRequest;
import com.omgservers.schema.master.entity.GetEntityResponse;
import com.omgservers.schema.master.entity.SyncEntityRequest;
import com.omgservers.schema.master.entity.SyncEntityResponse;
import com.omgservers.schema.master.entity.ViewEntitiesRequest;
import com.omgservers.schema.master.entity.ViewEntitiesResponse;
import io.smallrye.mutiny.Uni;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

@Tag(name = "Entity Master API")
@Path("/service/v1/master/entity/request")
public interface EntityApi {

    @POST
    @Path("/get-entity")
    Uni<GetEntityResponse> execute(GetEntityRequest request);

    @POST
    @Path("/find-entity")
    Uni<FindEntityResponse> execute(FindEntityRequest request);

    @POST
    @Path("/view-entities")
    Uni<ViewEntitiesResponse> execute(ViewEntitiesRequest request);

    @POST
    @Path("/sync-entity")
    Uni<SyncEntityResponse> execute(SyncEntityRequest request);

    @POST
    @Path("/delete-entity")
    Uni<DeleteEntityResponse> execute(DeleteEntityRequest request);
}
