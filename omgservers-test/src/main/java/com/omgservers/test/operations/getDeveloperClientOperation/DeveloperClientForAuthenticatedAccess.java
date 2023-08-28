package com.omgservers.test.operations.getDeveloperClientOperation;

import com.omgservers.dto.developer.CreateProjectDeveloperRequest;
import com.omgservers.dto.developer.CreateProjectDeveloperResponse;
import com.omgservers.dto.developer.CreateTokenDeveloperRequest;
import com.omgservers.dto.developer.CreateTokenDeveloperResponse;
import com.omgservers.dto.developer.CreateVersionDeveloperRequest;
import com.omgservers.dto.developer.CreateVersionDeveloperResponse;
import com.omgservers.dto.developer.GetVersionStatusDeveloperRequest;
import com.omgservers.dto.developer.GetVersionStatusDeveloperResponse;
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
    @Path("/create-token")
    Uni<CreateTokenDeveloperResponse> createToken(@NotBody String token, CreateTokenDeveloperRequest request);

    default CreateTokenDeveloperResponse createToken(long timeout, @NotBody String token, CreateTokenDeveloperRequest request) {
        return createToken(token, request)
                .await().atMost(Duration.ofSeconds(timeout));
    }

    @PUT
    @Path("/create-project")
    Uni<CreateProjectDeveloperResponse> createProject(@NotBody String token, CreateProjectDeveloperRequest request);

    default CreateProjectDeveloperResponse createProject(long timeout, @NotBody String token, CreateProjectDeveloperRequest request) {
        return createProject(token, request)
                .await().atMost(Duration.ofSeconds(timeout));
    }

    @PUT
    @Path("/create-version")
    Uni<CreateVersionDeveloperResponse> createVersion(@NotBody String token, CreateVersionDeveloperRequest request);

    default CreateVersionDeveloperResponse createVersion(long timeout, @NotBody String token, CreateVersionDeveloperRequest request) {
        return createVersion(token, request)
                .await().atMost(Duration.ofSeconds(timeout));
    }

    @PUT
    @Path("/has-version-deployed")
    Uni<GetVersionStatusDeveloperResponse> getVersionStatus(@NotBody String token, GetVersionStatusDeveloperRequest request);

    default GetVersionStatusDeveloperResponse getVersionStatus(long timeout, @NotBody String token, GetVersionStatusDeveloperRequest request) {
        return getVersionStatus(token, request)
                .await().atMost(Duration.ofSeconds(timeout));
    }
}
