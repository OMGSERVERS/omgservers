package com.omgservers.module.system.impl.service.containerService;

import com.omgservers.model.dto.system.DeleteContainerRequest;
import com.omgservers.model.dto.system.DeleteContainerResponse;
import com.omgservers.model.dto.system.FindContainerRequest;
import com.omgservers.model.dto.system.FindContainerResponse;
import com.omgservers.model.dto.system.GetContainerRequest;
import com.omgservers.model.dto.system.GetContainerResponse;
import com.omgservers.model.dto.system.RunContainerRequest;
import com.omgservers.model.dto.system.RunContainerResponse;
import com.omgservers.model.dto.system.StopContainerRequest;
import com.omgservers.model.dto.system.StopContainerResponse;
import com.omgservers.model.dto.system.SyncContainerRequest;
import com.omgservers.model.dto.system.SyncContainerResponse;
import io.smallrye.mutiny.Uni;
import jakarta.validation.Valid;

public interface ContainerService {

    Uni<GetContainerResponse> getContainer(@Valid GetContainerRequest request);

    Uni<FindContainerResponse> findContainer(@Valid FindContainerRequest request);

    Uni<SyncContainerResponse> syncContainer(@Valid SyncContainerRequest request);

    Uni<DeleteContainerResponse> deleteContainer(@Valid DeleteContainerRequest request);

    Uni<RunContainerResponse> runContainer(@Valid RunContainerRequest request);

    Uni<StopContainerResponse> stopContainer(@Valid StopContainerRequest request);
}
