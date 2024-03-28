package com.omgservers.service.module.server.impl.service.webService;

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

public interface WebService {

    Uni<GetServerResponse> getServer(GetServerRequest request);

    Uni<SyncServerResponse> syncServer(SyncServerRequest request);

    Uni<DeleteServerResponse> deleteServer(DeleteServerRequest request);

    Uni<GetServerContainerResponse> getServerContainer(GetServerContainerRequest request);

    Uni<ViewServerContainersResponse> viewServerContainers(ViewServerContainersRequest request);

    Uni<SyncServerContainerResponse> syncServerContainer(SyncServerContainerRequest request);

    Uni<DeleteServerContainerResponse> deleteServerContainer(DeleteServerContainerRequest request);
}
