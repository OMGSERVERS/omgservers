package com.omgservers.service.entrypoint.admin.impl.service.adminService.impl.method;

import com.omgservers.schema.entrypoint.admin.CreateTokenAdminRequest;
import com.omgservers.schema.entrypoint.admin.CreateTokenAdminResponse;
import com.omgservers.schema.module.user.CreateTokenRequest;
import com.omgservers.schema.module.user.CreateTokenResponse;
import com.omgservers.service.module.user.UserModule;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
class CreateTokenMethodImpl implements CreateTokenMethod {

    final UserModule userModule;

    @Override
    public Uni<CreateTokenAdminResponse> execute(final CreateTokenAdminRequest request) {
        log.debug("Requested, {}", request);

        final var userId = request.getUserId();
        final var password = request.getPassword();

        return createToken(userId, password)
                .map(CreateTokenAdminResponse::new);
    }

    Uni<String> createToken(final Long userId, final String password) {
        final var createTokenRequest = new CreateTokenRequest(userId, password);
        return userModule.getService().createToken(createTokenRequest)
                .map(CreateTokenResponse::getRawToken);
    }
}
