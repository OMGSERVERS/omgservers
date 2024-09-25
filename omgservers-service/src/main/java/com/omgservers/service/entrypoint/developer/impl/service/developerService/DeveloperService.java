package com.omgservers.service.entrypoint.developer.impl.service.developerService;

import com.omgservers.schema.entrypoint.developer.BuildTenantVersionDeveloperRequest;
import com.omgservers.schema.entrypoint.developer.BuildTenantVersionDeveloperResponse;
import com.omgservers.schema.entrypoint.developer.CreateTenantProjectDeveloperRequest;
import com.omgservers.schema.entrypoint.developer.CreateTenantProjectDeveloperResponse;
import com.omgservers.schema.entrypoint.developer.CreateTenantStageDeveloperRequest;
import com.omgservers.schema.entrypoint.developer.CreateTenantStageDeveloperResponse;
import com.omgservers.schema.entrypoint.developer.CreateTenantVersionDeveloperRequest;
import com.omgservers.schema.entrypoint.developer.CreateTenantVersionDeveloperResponse;
import com.omgservers.schema.entrypoint.developer.CreateTokenDeveloperRequest;
import com.omgservers.schema.entrypoint.developer.CreateTokenDeveloperResponse;
import com.omgservers.schema.entrypoint.developer.DeleteTenantDeploymentDeveloperRequest;
import com.omgservers.schema.entrypoint.developer.DeleteTenantDeploymentDeveloperResponse;
import com.omgservers.schema.entrypoint.developer.DeleteTenantProjectDeveloperRequest;
import com.omgservers.schema.entrypoint.developer.DeleteTenantProjectDeveloperResponse;
import com.omgservers.schema.entrypoint.developer.DeleteTenantStageDeveloperRequest;
import com.omgservers.schema.entrypoint.developer.DeleteTenantStageDeveloperResponse;
import com.omgservers.schema.entrypoint.developer.DeleteTenantVersionDeveloperRequest;
import com.omgservers.schema.entrypoint.developer.DeleteTenantVersionDeveloperResponse;
import com.omgservers.schema.entrypoint.developer.DeployTenantVersionDeveloperRequest;
import com.omgservers.schema.entrypoint.developer.DeployTenantVersionDeveloperResponse;
import com.omgservers.schema.entrypoint.developer.GetTenantDashboardDeveloperRequest;
import com.omgservers.schema.entrypoint.developer.GetTenantDashboardDeveloperResponse;
import com.omgservers.schema.entrypoint.developer.GetTenantDeploymentDashboardDeveloperRequest;
import com.omgservers.schema.entrypoint.developer.GetTenantDeploymentDashboardDeveloperResponse;
import com.omgservers.schema.entrypoint.developer.GetTenantProjectDashboardDeveloperRequest;
import com.omgservers.schema.entrypoint.developer.GetTenantProjectDashboardDeveloperResponse;
import com.omgservers.schema.entrypoint.developer.GetTenantStageDashboardDeveloperRequest;
import com.omgservers.schema.entrypoint.developer.GetTenantStageDashboardDeveloperResponse;
import com.omgservers.schema.entrypoint.developer.GetTenantVersionDashboardDeveloperRequest;
import com.omgservers.schema.entrypoint.developer.GetTenantVersionDashboardDeveloperResponse;
import io.smallrye.mutiny.Uni;
import jakarta.validation.Valid;

public interface DeveloperService {

    Uni<CreateTokenDeveloperResponse> createToken(@Valid CreateTokenDeveloperRequest request);

    Uni<GetTenantDashboardDeveloperResponse> getTenantDashboard(@Valid GetTenantDashboardDeveloperRequest request);

    Uni<CreateTenantProjectDeveloperResponse> createTenantProject(@Valid CreateTenantProjectDeveloperRequest request);

    Uni<GetTenantProjectDashboardDeveloperResponse> getTenantProjectDashboard(
            @Valid GetTenantProjectDashboardDeveloperRequest request);

    Uni<DeleteTenantProjectDeveloperResponse> deleteTenantProject(@Valid DeleteTenantProjectDeveloperRequest request);

    Uni<CreateTenantStageDeveloperResponse> createTenantStage(@Valid CreateTenantStageDeveloperRequest request);

    Uni<GetTenantStageDashboardDeveloperResponse> getTenantStageDashboard(
            @Valid GetTenantStageDashboardDeveloperRequest request);

    Uni<DeleteTenantStageDeveloperResponse> deleteTenantStage(@Valid DeleteTenantStageDeveloperRequest request);

    Uni<CreateTenantVersionDeveloperResponse> createTenantVersion(@Valid CreateTenantVersionDeveloperRequest request);

    Uni<BuildTenantVersionDeveloperResponse> buildTenantVersion(@Valid BuildTenantVersionDeveloperRequest request);

    Uni<GetTenantVersionDashboardDeveloperResponse> getTenantVersionDashboard(
            @Valid GetTenantVersionDashboardDeveloperRequest request);

    Uni<DeleteTenantVersionDeveloperResponse> deleteTenantVersion(@Valid DeleteTenantVersionDeveloperRequest request);

    Uni<DeployTenantVersionDeveloperResponse> deployTenantVersion(@Valid DeployTenantVersionDeveloperRequest request);

    Uni<GetTenantDeploymentDashboardDeveloperResponse> getTenantDeploymentDashboard(
            @Valid GetTenantDeploymentDashboardDeveloperRequest request);

    Uni<DeleteTenantDeploymentDeveloperResponse> deleteTenantDeployment(
            @Valid DeleteTenantDeploymentDeveloperRequest request);
}
