package com.omgservers.test.operations.getDeveloperClientOperation;

import com.omgservers.dto.developer.CreateProjectDeveloperRequest;
import com.omgservers.dto.developer.CreateProjectDeveloperResponse;
import com.omgservers.dto.developer.CreateTokenDeveloperRequest;
import com.omgservers.dto.developer.CreateTokenDeveloperResponse;
import com.omgservers.dto.developer.CreateVersionDeveloperRequest;
import com.omgservers.dto.developer.CreateVersionDeveloperResponse;
import com.omgservers.dto.developer.GetVersionStatusDeveloperRequest;
import com.omgservers.dto.developer.GetVersionStatusDeveloperResponse;
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
    @Path("/get-version-status")
    Uni<GetVersionStatusDeveloperResponse> getVersionStatus(GetVersionStatusDeveloperRequest request);

    default GetVersionStatusDeveloperResponse getVersionStatus(long timeout, GetVersionStatusDeveloperRequest request) {
        return getVersionStatus(request)
                .await().atMost(Duration.ofSeconds(timeout));
    }
}
