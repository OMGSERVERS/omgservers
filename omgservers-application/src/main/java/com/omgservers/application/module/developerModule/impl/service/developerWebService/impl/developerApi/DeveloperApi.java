package com.omgservers.application.module.developerModule.impl.service.developerWebService.impl.developerApi;

import com.omgservers.application.module.developerModule.impl.service.developerHelpService.request.CreateProjectHelpRequest;
import com.omgservers.application.module.developerModule.impl.service.developerHelpService.request.CreateTokenHelpRequest;
import com.omgservers.application.module.developerModule.impl.service.developerHelpService.request.CreateVersionHelpRequest;
import com.omgservers.application.module.developerModule.impl.service.developerHelpService.request.GetVersionStatusHelpRequest;
import com.omgservers.application.module.developerModule.impl.service.developerHelpService.response.CreateProjectHelpResponse;
import com.omgservers.application.module.developerModule.impl.service.developerHelpService.response.CreateTokenHelpResponse;
import com.omgservers.application.module.developerModule.impl.service.developerHelpService.response.CreateVersionHelpResponse;
import com.omgservers.application.module.developerModule.impl.service.developerHelpService.response.GetVersionStatusHelpResponse;
import io.smallrye.mutiny.Uni;

import jakarta.ws.rs.*;

import java.time.Duration;

@Path("/omgservers/developer-api/v1/requests")
public interface DeveloperApi {

    @PUT
    @Path("/create-token")
    Uni<CreateTokenHelpResponse> createToken(CreateTokenHelpRequest request);

    default CreateTokenHelpResponse createToken(long timeout, CreateTokenHelpRequest request) {
        return createToken(request)
                .await().atMost(Duration.ofSeconds(timeout));
    }

    @PUT
    @Path("/create-project")
    Uni<CreateProjectHelpResponse> createProject(CreateProjectHelpRequest request);

    default CreateProjectHelpResponse createProject(long timeout, CreateProjectHelpRequest request) {
        return createProject(request)
                .await().atMost(Duration.ofSeconds(timeout));
    }

    @PUT
    @Path("/create-version")
    Uni<CreateVersionHelpResponse> createVersion(CreateVersionHelpRequest request);

    default CreateVersionHelpResponse createVersion(long timeout, CreateVersionHelpRequest request) {
        return createVersion(request)
                .await().atMost(Duration.ofSeconds(timeout));
    }

    @PUT
    @Path("/has-version-deployed")
    Uni<GetVersionStatusHelpResponse> getVersionStatus(GetVersionStatusHelpRequest request);

    default GetVersionStatusHelpResponse getVersionStatus(long timeout, GetVersionStatusHelpRequest request) {
        return getVersionStatus(request)
                .await().atMost(Duration.ofSeconds(timeout));
    }
}
