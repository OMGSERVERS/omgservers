package com.omgservers.service.entrypoint.admin.impl.service.adminService.impl.method;

import com.omgservers.schema.entrypoint.admin.CreateTokenAdminRequest;
import com.omgservers.schema.entrypoint.admin.CreateTokenAdminResponse;
import com.omgservers.schema.module.user.CreateTokenRequest;
import com.omgservers.schema.module.user.CreateTokenResponse;
import com.omgservers.service.module.alias.AliasModule;
import com.omgservers.service.module.user.UserModule;
import com.omgservers.service.operation.getIdByUser.GetIdByUserOperation;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
class CreateTokenMethodImpl implements CreateTokenMethod {

    final AliasModule aliasModule;
    final UserModule userModule;

    final GetIdByUserOperation getIdByUserOperation;

    @Override
    public Uni<CreateTokenAdminResponse> execute(final CreateTokenAdminRequest request) {
        log.trace("{}", request);

        final var user = request.getUser();
        return getIdByUserOperation.execute(user)
                .flatMap(userId -> {
                    final var password = request.getPassword();
                    return createToken(userId, password)
                            .invoke(token -> log.info("A token was issued for the admin user {}.", user))
                            .map(CreateTokenAdminResponse::new);
                });
    }

    Uni<String> createToken(final Long userId, final String password) {
        final var createTokenRequest = new CreateTokenRequest(userId, password);
        return userModule.getService().createToken(createTokenRequest)
                .map(CreateTokenResponse::getRawToken);
    }
}
