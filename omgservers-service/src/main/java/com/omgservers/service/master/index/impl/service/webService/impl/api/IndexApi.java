package com.omgservers.service.master.index.impl.service.webService.impl.api;

import com.omgservers.schema.master.index.GetIndexRequest;
import com.omgservers.schema.master.index.GetIndexResponse;
import com.omgservers.schema.master.index.SyncIndexRequest;
import com.omgservers.schema.master.index.SyncIndexResponse;
import io.smallrye.mutiny.Uni;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

@Tag(name = "Index Master API")
@Path("/service/v1/master/index/request")
public interface IndexApi {

    @POST
    @Path("/get-index")
    Uni<GetIndexResponse> execute(GetIndexRequest request);

    @POST
    @Path("/sync-index")
    Uni<SyncIndexResponse> execute(SyncIndexRequest request);
}
