package com.omgservers.service.master.node.impl.service.webService.impl.api;

import com.omgservers.schema.master.node.AcquireNodeRequest;
import com.omgservers.schema.master.node.AcquireNodeResponse;
import io.smallrye.mutiny.Uni;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

@Tag(name = "Node Master API")
@Path("/service/v1/master/node/request")
public interface NodeApi {

    @POST
    @Path("/acquire-node")
    Uni<AcquireNodeResponse> execute(AcquireNodeRequest request);
}
