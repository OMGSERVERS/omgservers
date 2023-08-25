package com.omgservers.application.module.developerModule.impl.service.developerWebService.impl.developerApi;

import com.omgservers.dto.developerModule.CreateProjectDeveloperRequest;
import com.omgservers.dto.developerModule.CreateTokenDeveloperRequest;
import com.omgservers.dto.developerModule.CreateVersionDeveloperRequest;
import com.omgservers.dto.developerModule.GetVersionStatusDeveloperRequest;
import com.omgservers.dto.developerModule.CreateProjectDeveloperResponse;
import com.omgservers.dto.developerModule.CreateTokenDeveloperResponse;
import com.omgservers.dto.developerModule.CreateVersionDeveloperResponse;
import com.omgservers.dto.developerModule.GetVersionStatusDeveloperResponse;
import io.smallrye.mutiny.Uni;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;

import java.time.Duration;

@Path("/omgservers/developer-api/v1/requests")
public interface DeveloperApi {

    @PUT
    @Path("/create-token")
    Uni<CreateTokenDeveloperResponse> createToken(CreateTokenDeveloperRequest request);

    default CreateTokenDeveloperResponse createToken(long timeout, CreateTokenDeveloperRequest request) {
        return createToken(request)
                .await().atMost(Duration.ofSeconds(timeout));
    }

    @PUT
    @Path("/create-project")
    Uni<CreateProjectDeveloperResponse> createProject(CreateProjectDeveloperRequest request);

    default CreateProjectDeveloperResponse createProject(long timeout, CreateProjectDeveloperRequest request) {
        return createProject(request)
                .await().atMost(Duration.ofSeconds(timeout));
    }

    @PUT
    @Path("/create-version")
    Uni<CreateVersionDeveloperResponse> createVersion(CreateVersionDeveloperRequest request);

    default CreateVersionDeveloperResponse createVersion(long timeout, CreateVersionDeveloperRequest request) {
        return createVersion(request)
                .await().atMost(Duration.ofSeconds(timeout));
    }

    @PUT
    @Path("/has-version-deployed")
    Uni<GetVersionStatusDeveloperResponse> getVersionStatus(GetVersionStatusDeveloperRequest request);

    default GetVersionStatusDeveloperResponse getVersionStatus(long timeout, GetVersionStatusDeveloperRequest request) {
        return getVersionStatus(request)
                .await().atMost(Duration.ofSeconds(timeout));
    }
}
