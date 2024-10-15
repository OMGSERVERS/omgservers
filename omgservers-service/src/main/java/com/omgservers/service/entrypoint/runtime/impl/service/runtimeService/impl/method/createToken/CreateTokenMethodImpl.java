package com.omgservers.service.entrypoint.runtime.impl.service.runtimeService.impl.method.createToken;

import com.omgservers.schema.entrypoint.runtime.CreateTokenRuntimeRequest;
import com.omgservers.schema.entrypoint.runtime.CreateTokenRuntimeResponse;
import com.omgservers.schema.model.runtime.RuntimeModel;
import com.omgservers.schema.model.user.UserRoleEnum;
import com.omgservers.schema.module.runtime.GetRuntimeRequest;
import com.omgservers.schema.module.runtime.GetRuntimeResponse;
import com.omgservers.schema.module.user.CreateTokenRequest;
import com.omgservers.schema.module.user.CreateTokenResponse;
import com.omgservers.service.module.runtime.RuntimeModule;
import com.omgservers.service.module.user.UserModule;
import com.omgservers.service.operation.issueJwtToken.IssueJwtTokenOperation;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
class CreateTokenMethodImpl implements CreateTokenMethod {

    final RuntimeModule runtimeModule;
    final UserModule userModule;

    final IssueJwtTokenOperation issueJwtTokenOperation;

    @Override
    public Uni<CreateTokenRuntimeResponse> createToken(final CreateTokenRuntimeRequest request) {
        log.debug("Create token, request={}", request);

        final var runtimeId = request.getRuntimeId();
        final var password = request.getPassword();

        return getRuntime(runtimeId)
                .flatMap(runtime -> {
                    final var userId = runtime.getUserId();
                    return createUserToken(userId, password)
                            .map(userToken -> {
                                final var apiToken = issueJwtTokenOperation
                                        .issueRuntimeJwtToken(runtimeId);
                                final var wsToken = issueJwtTokenOperation
                                        .issueWsJwtToken(userId, runtimeId, UserRoleEnum.RUNTIME);

                                return new CreateTokenRuntimeResponse(apiToken, wsToken);
                            });
                });
    }

    Uni<String> createUserToken(final Long userId, final String password) {
        final var createTokenRequest = new CreateTokenRequest(userId, password);
        return userModule.getService().createToken(createTokenRequest)
                .map(CreateTokenResponse::getRawToken);
    }

    Uni<RuntimeModel> getRuntime(final Long id) {
        final var request = new GetRuntimeRequest(id);
        return runtimeModule.getService().getRuntime(request)
                .map(GetRuntimeResponse::getRuntime);
    }
}
