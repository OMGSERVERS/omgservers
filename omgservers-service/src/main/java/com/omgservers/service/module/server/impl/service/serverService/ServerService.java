package com.omgservers.service.module.server.impl.service.serverService;

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
import jakarta.validation.Valid;

public interface ServerService {

    Uni<GetServerResponse> getServer(@Valid GetServerRequest request);

    Uni<SyncServerResponse> syncServer(@Valid SyncServerRequest request);

    Uni<DeleteServerResponse> deleteServer(@Valid DeleteServerRequest request);

    Uni<GetServerContainerResponse> getServerContainer(@Valid GetServerContainerRequest request);

    Uni<ViewServerContainersResponse> viewServerContainers(@Valid ViewServerContainersRequest request);

    Uni<SyncServerContainerResponse> syncServerContainer(@Valid SyncServerContainerRequest request);

    Uni<DeleteServerContainerResponse> deleteServerContainer(@Valid DeleteServerContainerRequest request);
}
