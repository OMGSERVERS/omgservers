package com.omgservers.platforms.integrationtest.cli;

import com.omgservers.application.module.adminModule.impl.service.adminHelpService.request.CreateDeveloperHelpRequest;
import com.omgservers.application.module.adminModule.impl.service.adminHelpService.request.CreateTenantHelpRequest;
import com.omgservers.application.module.internalModule.impl.service.indexHelpService.request.DeleteIndexHelpRequest;
import com.omgservers.application.module.internalModule.impl.service.indexHelpService.request.GetIndexHelpRequest;
import com.omgservers.application.module.internalModule.impl.service.indexHelpService.request.SyncIndexHelpRequest;
import com.omgservers.application.module.internalModule.impl.service.serviceAccountHelpService.request.DeleteServiceAccountHelpRequest;
import com.omgservers.application.module.internalModule.impl.service.serviceAccountHelpService.request.GetServiceAccountHelpRequest;
import com.omgservers.application.module.internalModule.impl.service.serviceAccountHelpService.request.SyncServiceAccountHelpRequest;
import com.omgservers.application.module.internalModule.model.index.IndexModel;
import com.omgservers.application.module.internalModule.model.serviceAccount.ServiceAccountModel;
import com.omgservers.application.module.adminModule.impl.service.adminHelpService.response.CreateDeveloperHelpResponse;
import com.omgservers.application.module.adminModule.impl.service.adminHelpService.response.PingServerHelpResponse;
import com.omgservers.application.module.internalModule.model.serviceAccount.ServiceAccountModelFactory;
import com.omgservers.platforms.integrationtest.operations.getAdminClientOperation.AdminClientForAdminAccount;
import com.omgservers.platforms.integrationtest.operations.getAdminClientOperation.GetAdminClientOperation;
import com.omgservers.platforms.integrationtest.operations.getConfigOperation.GetConfigOperation;
import io.quarkus.elytron.security.common.BcryptUtil;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.net.URI;

@Slf4j
@ApplicationScoped
@RequiredArgsConstructor
public class AdminCli {
    private final int TIMEOUT = 10;

    final GetConfigOperation getConfigOperation;
    final GetAdminClientOperation getAdminClientOperation;

    final ServiceAccountModelFactory serviceAccountModelFactory;

    AdminClientForAdminAccount adminClient;

    public void createClient() {
        final var uri = getConfigOperation.getServers().get(0).externalAddress();
        createClient(uri);
    }

    public void createClient(URI uri) {
        adminClient = getAdminClientOperation.getAdminClientForAdminAccount(uri);
    }

    public AdminClientForAdminAccount getClient() {
        return adminClient;
    }

    public PingServerHelpResponse pingServer() {
        return adminClient.pingServer(TIMEOUT);
    }

    public IndexModel getIndex(String name) {
        final var getIndexResponse = adminClient.getIndex(TIMEOUT, new GetIndexHelpRequest(name));
        return getIndexResponse.getIndex();
    }

    public void syncIndex(IndexModel index) {
        adminClient.syncIndex(TIMEOUT, new SyncIndexHelpRequest(index));
    }

    public void deleteIndex(Long id) {
        adminClient.deleteIndex(TIMEOUT, new DeleteIndexHelpRequest(id));
    }

    public ServiceAccountModel getServiceAccount(String username) {
        final var getServiceAccountResponse = adminClient
                .getServiceAccount(TIMEOUT, new GetServiceAccountHelpRequest(username));
        return getServiceAccountResponse.getServiceAccount();
    }

    public void createServiceAccount(String username, String password) {
        final var serviceAccountModel = serviceAccountModelFactory.create(username, BcryptUtil.bcryptHash(password));
        syncServiceAccount(serviceAccountModel);
    }

    public void syncServiceAccount(ServiceAccountModel serviceAccount) {
        adminClient.syncServiceAccount(TIMEOUT, new SyncServiceAccountHelpRequest(serviceAccount));
    }

    public void deleteServiceAccount(Long id) {
        adminClient.deleteServiceAccount(TIMEOUT, new DeleteServiceAccountHelpRequest(id));
    }

    public Long createTenant(String title) {
        final var response = adminClient.createTenant(TIMEOUT, new CreateTenantHelpRequest(title));
        return response.getId();
    }

    public CreateDeveloperHelpResponse createDeveloper(Long tenantId) {
        return adminClient.createDeveloper(TIMEOUT, new CreateDeveloperHelpRequest(tenantId));
    }
}
