package com.omgservers.service.module.developer.impl.service.webService.impl.developerApi;

import com.omgservers.model.dto.developer.CreateProjectDeveloperRequest;
import com.omgservers.model.dto.developer.CreateProjectDeveloperResponse;
import com.omgservers.model.dto.developer.CreateTokenDeveloperRequest;
import com.omgservers.model.dto.developer.CreateTokenDeveloperResponse;
import com.omgservers.model.dto.developer.CreateVersionDeveloperRequest;
import com.omgservers.model.dto.developer.CreateVersionDeveloperResponse;
import com.omgservers.model.dto.developer.DeleteVersionDeveloperRequest;
import com.omgservers.model.dto.developer.DeleteVersionDeveloperResponse;
import io.smallrye.mutiny.Uni;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;

@Path("/omgservers/developer-api/v1/requests")
public interface DeveloperApi {

    @PUT
    @Path("/create-token")
    Uni<CreateTokenDeveloperResponse> createToken(CreateTokenDeveloperRequest request);

    @PUT
    @Path("/create-project")
    Uni<CreateProjectDeveloperResponse> createProject(CreateProjectDeveloperRequest request);

    @PUT
    @Path("/create-version")
    Uni<CreateVersionDeveloperResponse> createVersion(CreateVersionDeveloperRequest request);

    @PUT
    @Path("/delete-version")
    Uni<DeleteVersionDeveloperResponse> deleteVersion(DeleteVersionDeveloperRequest request);
}
