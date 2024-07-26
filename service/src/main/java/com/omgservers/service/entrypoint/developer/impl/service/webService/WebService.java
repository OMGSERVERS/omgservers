package com.omgservers.service.entrypoint.developer.impl.service.webService;

import com.omgservers.model.dto.developer.CreateProjectDeveloperRequest;
import com.omgservers.model.dto.developer.CreateProjectDeveloperResponse;
import com.omgservers.model.dto.developer.CreateTokenDeveloperRequest;
import com.omgservers.model.dto.developer.CreateTokenDeveloperResponse;
import com.omgservers.model.dto.developer.CreateVersionDeveloperRequest;
import com.omgservers.model.dto.developer.CreateVersionDeveloperResponse;
import com.omgservers.model.dto.developer.DeleteVersionDeveloperRequest;
import com.omgservers.model.dto.developer.DeleteVersionDeveloperResponse;
import com.omgservers.model.dto.developer.DeployVersionDeveloperRequest;
import com.omgservers.model.dto.developer.DeployVersionDeveloperResponse;
import com.omgservers.model.dto.developer.GetTenantDashboardDeveloperRequest;
import com.omgservers.model.dto.developer.GetTenantDashboardDeveloperResponse;
import com.omgservers.model.dto.developer.UploadVersionDeveloperRequest;
import com.omgservers.model.dto.developer.UploadVersionDeveloperResponse;
import io.smallrye.mutiny.Uni;

public interface WebService {

    Uni<CreateTokenDeveloperResponse> createToken(CreateTokenDeveloperRequest request);

    Uni<GetTenantDashboardDeveloperResponse> getTenantDashboard(GetTenantDashboardDeveloperRequest request);

    Uni<CreateProjectDeveloperResponse> createProject(CreateProjectDeveloperRequest request);

    Uni<CreateVersionDeveloperResponse> createVersion(CreateVersionDeveloperRequest request);

    Uni<UploadVersionDeveloperResponse> uploadVersion(UploadVersionDeveloperRequest request);

    Uni<DeployVersionDeveloperResponse> deployVersion(DeployVersionDeveloperRequest request);

    Uni<DeleteVersionDeveloperResponse> deleteVersion(DeleteVersionDeveloperRequest request);
}
