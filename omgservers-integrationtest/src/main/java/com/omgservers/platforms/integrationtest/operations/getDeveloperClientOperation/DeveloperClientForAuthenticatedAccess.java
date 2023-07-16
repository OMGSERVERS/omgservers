package com.omgservers.platforms.integrationtest.operations.getDeveloperClientOperation;

import com.omgservers.application.module.developerModule.impl.service.developerHelpService.request.CreateProjectHelpRequest;
import com.omgservers.application.module.developerModule.impl.service.developerHelpService.request.CreateVersionHelpRequest;
import com.omgservers.application.module.developerModule.impl.service.developerHelpService.request.GetVersionStatusHelpRequest;
import com.omgservers.application.module.developerModule.impl.service.developerHelpService.response.CreateProjectHelpResponse;
import com.omgservers.application.module.developerModule.impl.service.developerHelpService.response.CreateVersionHelpResponse;
import com.omgservers.application.module.developerModule.impl.service.developerHelpService.response.GetVersionStatusHelpResponse;
import io.quarkus.rest.client.reactive.NotBody;
import io.smallrye.mutiny.Uni;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import org.eclipse.microprofile.rest.client.annotation.ClientHeaderParam;

import java.time.Duration;

@Path("/omgservers/developer-api/v1/requests")
@ClientHeaderParam(name = "Authorization", value = "Bearer {token}")
public interface DeveloperClientForAuthenticatedAccess {

    @PUT
    @Path("/create-version")
    Uni<CreateVersionHelpResponse> createVersion(@NotBody String token, CreateVersionHelpRequest request);

    default CreateVersionHelpResponse createVersion(long timeout, String token, CreateVersionHelpRequest request) {
        return createVersion(token, request)
                .await().atMost(Duration.ofSeconds(timeout));
    }

    @PUT
    @Path("/create-project")
    Uni<CreateProjectHelpResponse> createProject(@NotBody String token, CreateProjectHelpRequest request);

    default CreateProjectHelpResponse createProject(long timeout, String token, CreateProjectHelpRequest request) {
        return createProject(token, request)
                .await().atMost(Duration.ofSeconds(timeout));
    }

    @PUT
    @Path("/has-version-deployed")
    Uni<GetVersionStatusHelpResponse> getVersionStatus(@NotBody String token, GetVersionStatusHelpRequest request);

    default GetVersionStatusHelpResponse getVersionStatus(long timeout, String token, GetVersionStatusHelpRequest request) {
        return getVersionStatus(token, request)
                .await().atMost(Duration.ofSeconds(timeout));
    }
}
