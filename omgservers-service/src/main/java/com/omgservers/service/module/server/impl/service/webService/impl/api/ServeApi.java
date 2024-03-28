package com.omgservers.service.module.server.impl.service.webService.impl.api;

import com.omgservers.model.dto.server.DeleteServerContainerRequest;
import com.omgservers.model.dto.server.DeleteServerContainerResponse;
import com.omgservers.model.dto.server.DeleteServerRequest;
import com.omgservers.model.dto.server.DeleteServerResponse;
import com.omgservers.model.dto.server.GetServerContainerRequest;
import com.omgservers.model.dto.server.GetServerContainerResponse;
import com.omgservers.model.dto.server.GetServerRequest;
import com.omgservers.model.dto.server.GetServerResponse;
import com.omgservers.model.dto.server.SyncServerContainerRequest;
import com.omgservers.model.dto.server.SyncServerContainerResponse;
import com.omgservers.model.dto.server.SyncServerRequest;
import com.omgservers.model.dto.server.SyncServerResponse;
import com.omgservers.model.dto.server.ViewServerContainersRequest;
import com.omgservers.model.dto.server.ViewServerContainersResponse;
import io.smallrye.mutiny.Uni;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

@Tag(name = "Server Module API")
@Path("/omgservers/v1/module/server/request")
public interface ServeApi {

    @PUT
    @Path("/get-server")
    Uni<GetServerResponse> getServer(GetServerRequest request);

    @PUT
    @Path("/sync-server")
    Uni<SyncServerResponse> syncServer(SyncServerRequest request);

    @PUT
    @Path("/delete-server")
    Uni<DeleteServerResponse> deleteServer(DeleteServerRequest request);

    @PUT
    @Path("/get-server-container")
    Uni<GetServerContainerResponse> getServerContainer(GetServerContainerRequest request);

    @PUT
    @Path("/view-server-containers")
    Uni<ViewServerContainersResponse> viewServerContainers(ViewServerContainersRequest request);

    @PUT
    @Path("/sync-server-container")
    Uni<SyncServerContainerResponse> syncServerContainer(SyncServerContainerRequest request);

    @PUT
    @Path("/delete-server-container")
    Uni<DeleteServerContainerResponse> deleteServerContainer(DeleteServerContainerRequest request);
}
