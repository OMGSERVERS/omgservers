package com.omgservers.service.entrypoint.developer.impl.service.developerService;

import com.omgservers.schema.entrypoint.developer.CreateProjectDeveloperRequest;
import com.omgservers.schema.entrypoint.developer.CreateProjectDeveloperResponse;
import com.omgservers.schema.entrypoint.developer.CreateTokenDeveloperRequest;
import com.omgservers.schema.entrypoint.developer.CreateTokenDeveloperResponse;
import com.omgservers.schema.entrypoint.developer.CreateVersionDeveloperRequest;
import com.omgservers.schema.entrypoint.developer.CreateVersionDeveloperResponse;
import com.omgservers.schema.entrypoint.developer.DeleteVersionDeveloperRequest;
import com.omgservers.schema.entrypoint.developer.DeleteVersionDeveloperResponse;
import com.omgservers.schema.entrypoint.developer.DeployVersionDeveloperRequest;
import com.omgservers.schema.entrypoint.developer.DeployVersionDeveloperResponse;
import com.omgservers.schema.entrypoint.developer.GetTenantDashboardDeveloperRequest;
import com.omgservers.schema.entrypoint.developer.GetTenantDashboardDeveloperResponse;
import com.omgservers.schema.entrypoint.developer.UploadVersionDeveloperRequest;
import com.omgservers.schema.entrypoint.developer.UploadVersionDeveloperResponse;
import io.smallrye.mutiny.Uni;
import jakarta.validation.Valid;

public interface DeveloperService {

    Uni<CreateTokenDeveloperResponse> createToken(@Valid CreateTokenDeveloperRequest request);

    Uni<GetTenantDashboardDeveloperResponse> getTenantDashboard(@Valid GetTenantDashboardDeveloperRequest request);

    Uni<CreateProjectDeveloperResponse> createProject(@Valid CreateProjectDeveloperRequest request);

    Uni<CreateVersionDeveloperResponse> createVersion(@Valid CreateVersionDeveloperRequest request);

    Uni<UploadVersionDeveloperResponse> uploadVersion(@Valid UploadVersionDeveloperRequest request);

    Uni<DeployVersionDeveloperResponse> deployVersion(@Valid DeployVersionDeveloperRequest request);

    Uni<DeleteVersionDeveloperResponse> deleteVersion(@Valid DeleteVersionDeveloperRequest request);
}
