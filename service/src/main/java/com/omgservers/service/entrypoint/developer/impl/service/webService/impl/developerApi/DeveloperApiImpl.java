package com.omgservers.service.entrypoint.developer.impl.service.webService.impl.developerApi;

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
import com.omgservers.model.user.UserRoleEnum;
import com.omgservers.service.entrypoint.developer.impl.service.webService.WebService;
import com.omgservers.service.operation.handleApiRequest.HandleApiRequestOperation;
import io.smallrye.mutiny.Uni;
import jakarta.annotation.security.PermitAll;
import jakarta.annotation.security.RolesAllowed;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jboss.resteasy.reactive.multipart.FileUpload;

import java.util.List;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
@RolesAllowed({UserRoleEnum.Names.DEVELOPER})
class DeveloperApiImpl implements DeveloperApi {

    final WebService webService;

    final HandleApiRequestOperation handleApiRequestOperation;

    @Override
    @PermitAll
    public Uni<CreateTokenDeveloperResponse> createToken(@NotNull final CreateTokenDeveloperRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::createToken);
    }

    @Override
    public Uni<GetTenantDashboardDeveloperResponse> getTenantDashboard(
            @NotNull final GetTenantDashboardDeveloperRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::getTenantDashboard);
    }

    @Override
    public Uni<CreateProjectDeveloperResponse> createProject(@NotNull final CreateProjectDeveloperRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::createProject);
    }

    @Override
    public Uni<CreateVersionDeveloperResponse> createVersion(@NotNull final CreateVersionDeveloperRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::createVersion);
    }

    @Override
    public Uni<UploadVersionDeveloperResponse> uploadVersion(final Long tenantId,
                                                             final Long stageId,
                                                             final List<FileUpload> files) {
        final var request = new UploadVersionDeveloperRequest(tenantId, stageId, files);
        return handleApiRequestOperation.handleApiRequest(log, request, webService::uploadVersion);
    }

    @Override
    public Uni<DeployVersionDeveloperResponse> deployVersion(@NotNull final DeployVersionDeveloperRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::deployVersion);
    }

    @Override
    public Uni<DeleteVersionDeveloperResponse> deleteVersion(@NotNull final DeleteVersionDeveloperRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::deleteVersion);
    }
}
