package com.omgservers.service.module.root.impl.service.webService.impl.api;

import com.omgservers.model.dto.root.DeleteRootRequest;
import com.omgservers.model.dto.root.DeleteRootResponse;
import com.omgservers.model.dto.root.GetRootRequest;
import com.omgservers.model.dto.root.GetRootResponse;
import com.omgservers.model.dto.root.SyncRootRequest;
import com.omgservers.model.dto.root.SyncRootResponse;
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
}
