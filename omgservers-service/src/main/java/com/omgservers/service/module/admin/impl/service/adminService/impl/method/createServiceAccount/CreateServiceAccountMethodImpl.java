package com.omgservers.service.module.admin.impl.service.adminService.impl.method.createServiceAccount;

import com.omgservers.model.dto.admin.CreateServiceAccountAdminRequest;
import com.omgservers.model.dto.admin.CreateServiceAccountAdminResponse;
import com.omgservers.service.factory.ServiceAccountModelFactory;
import com.omgservers.service.module.system.SystemModule;
import com.omgservers.service.operation.getConfig.GetConfigOperation;
import io.quarkus.elytron.security.common.BcryptUtil;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class CreateServiceAccountMethodImpl implements CreateServiceAccountMethod {

    final SystemModule systemModule;

    final GetConfigOperation getConfigOperation;

    final ServiceAccountModelFactory serviceAccountModelFactory;

    @Override
    public Uni<CreateServiceAccountAdminResponse> createServiceAccount(final CreateServiceAccountAdminRequest request) {
        log.debug("Create service account, request={}", request);

        final var username = request.getUsername();
        final var password = request.getPassword();

        final var passwordHash = BcryptUtil.bcryptHash(password);
        final var serviceAccount = serviceAccountModelFactory.create(username, passwordHash);

        return systemModule.getShortcutService().syncServiceAccount(serviceAccount)
                .replaceWith(new CreateServiceAccountAdminResponse(serviceAccount));
    }
}
