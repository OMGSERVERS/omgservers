package com.omgservers.module.system.impl.service.containerService;

import com.omgservers.model.dto.internal.DeleteContainerRequest;
import com.omgservers.model.dto.internal.DeleteContainerResponse;
import com.omgservers.model.dto.internal.GetContainerRequest;
import com.omgservers.model.dto.internal.GetContainerResponse;
import com.omgservers.model.dto.internal.RunContainerRequest;
import com.omgservers.model.dto.internal.RunContainerResponse;
import com.omgservers.model.dto.internal.StopContainerRequest;
import com.omgservers.model.dto.internal.StopContainerResponse;
import com.omgservers.model.dto.internal.SyncContainerRequest;
import com.omgservers.model.dto.internal.SyncContainerResponse;
import io.smallrye.mutiny.Uni;

public interface ContainerService {

    Uni<GetContainerResponse> getContainer(GetContainerRequest request);

    Uni<SyncContainerResponse> syncContainer(SyncContainerRequest request);

    Uni<DeleteContainerResponse> deleteContainer(DeleteContainerRequest request);

    Uni<RunContainerResponse> runContainer(RunContainerRequest request);

    Uni<StopContainerResponse> stopContainer(StopContainerRequest request);
}
