package com.omgservers.module.system.impl.service.containerService;

import com.omgservers.dto.internal.DeleteContainerRequest;
import com.omgservers.dto.internal.DeleteContainerResponse;
import com.omgservers.dto.internal.GetContainerRequest;
import com.omgservers.dto.internal.GetContainerResponse;
import com.omgservers.dto.internal.RunContainerRequest;
import com.omgservers.dto.internal.RunContainerResponse;
import com.omgservers.dto.internal.StopContainerRequest;
import com.omgservers.dto.internal.StopContainerResponse;
import com.omgservers.dto.internal.SyncContainerRequest;
import com.omgservers.dto.internal.SyncContainerResponse;
import io.smallrye.mutiny.Uni;

public interface ContainerService {

    Uni<GetContainerResponse> getContainer(GetContainerRequest request);

    Uni<SyncContainerResponse> syncContainer(SyncContainerRequest request);

    Uni<DeleteContainerResponse> deleteContainer(DeleteContainerRequest request);

    Uni<RunContainerResponse> runContainer(RunContainerRequest request);

    Uni<StopContainerResponse> stopContainer(StopContainerRequest request);
}
