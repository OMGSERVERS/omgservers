package com.omgservers.platforms.integrationtest.cli;

import com.omgservers.dto.tenant.GetTenantShardedRequest;
import com.omgservers.dto.tenant.HasProjectPermissionShardedRequest;
import com.omgservers.dto.tenant.HasStagePermissionShardedRequest;
import com.omgservers.dto.tenant.HasTenantPermissionShardedRequest;
import com.omgservers.model.projectPermission.ProjectPermissionEnum;
import com.omgservers.model.stagePermission.StagePermissionEnum;
import com.omgservers.model.tenant.TenantModel;
import com.omgservers.model.tenantPermission.TenantPermissionEnum;
import com.omgservers.platforms.integrationtest.operations.getTenantServiceApiClientOperation.TenantServiceApiClient;
import com.omgservers.platforms.integrationtest.operations.getConfigOperation.GetConfigOperation;
import com.omgservers.platforms.integrationtest.operations.getTenantServiceApiClientOperation.GetTenantServiceApiClientOperation;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.net.URI;

@Slf4j
@ApplicationScoped
@RequiredArgsConstructor
public class TenantCli {
    private final int TIMEOUT = 10;

    final GetConfigOperation getConfigOperation;
    final GetTenantServiceApiClientOperation getTenantServiceApiClientOperation;

    TenantServiceApiClient tenantClient;

    public void createClient() {
        final var uri = getConfigOperation.getServers().get(0).externalAddress();
        createClient(uri);
    }

    public void createClient(URI uri) {
        tenantClient = getTenantServiceApiClientOperation.getClient(uri);
    }

    public TenantServiceApiClient getClient() {
        return tenantClient;
    }

    public TenantModel getTenant(Long id) {
        final var response = tenantClient.getTenant(TIMEOUT, new GetTenantShardedRequest(id));
        return response.getTenant();
    }

    public Boolean hasTenantPermission(Long tenantId, Long userId, TenantPermissionEnum permission) {
        final var response = tenantClient
                .hasTenantPermission(TIMEOUT, new HasTenantPermissionShardedRequest(tenantId, userId, permission));
        return response.getResult();
    }

    public Boolean hasProjectPermission(Long tenantId, Long projectId, Long userId, ProjectPermissionEnum permission) {
        final var response = tenantClient
                .hasProjectPermission(TIMEOUT, new HasProjectPermissionShardedRequest(tenantId, projectId, userId, permission));
        return response.getResult();
    }

    public Boolean hasStagePermission(Long tenantId, Long stageId, Long userId, StagePermissionEnum permission) {
        final var response = tenantClient
                .hasStagePermission(TIMEOUT, new HasStagePermissionShardedRequest(tenantId, stageId, userId, permission));
        return response.getResult();
    }
}
