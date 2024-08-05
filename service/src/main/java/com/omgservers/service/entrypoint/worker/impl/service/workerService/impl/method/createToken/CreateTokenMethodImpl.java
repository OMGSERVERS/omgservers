package com.omgservers.service.entrypoint.worker.impl.service.workerService.impl.method.createToken;

import com.omgservers.schema.entrypoint.worker.CreateTokenWorkerRequest;
import com.omgservers.schema.entrypoint.worker.CreateTokenWorkerResponse;
import com.omgservers.schema.model.exception.ExceptionQualifierEnum;
import com.omgservers.schema.model.runtime.RuntimeModel;
import com.omgservers.schema.model.user.UserRoleEnum;
import com.omgservers.schema.module.runtime.GetRuntimeRequest;
import com.omgservers.schema.module.runtime.GetRuntimeResponse;
import com.omgservers.schema.module.user.CreateTokenRequest;
import com.omgservers.schema.module.user.CreateTokenResponse;
import com.omgservers.service.exception.ServerSideBadRequestException;
import com.omgservers.service.module.runtime.RuntimeModule;
import com.omgservers.service.module.user.UserModule;
import com.omgservers.service.server.operation.issueJwtToken.IssueJwtTokenOperation;
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
    public Uni<CreateTokenWorkerResponse> createToken(final CreateTokenWorkerRequest request) {
        log.debug("Create token, request={}", request);

        final var userId = request.getUserId();
        final var password = request.getPassword();

        return createApiToken(userId, password)
                .flatMap(apiToken -> {
                    final var runtimeId = request.getRuntimeId();
                    return getRuntime(runtimeId)
                            .map(runtime -> {
                                if (runtime.getUserId().equals(userId)) {
                                    final var wsToken = issueJwtTokenOperation
                                            .issueWsJwtToken(userId, runtimeId, UserRoleEnum.WORKER);
                                    return new CreateTokenWorkerResponse(apiToken, wsToken);
                                } else {
                                    throw new ServerSideBadRequestException(ExceptionQualifierEnum.RUNTIME_ID_WRONG,
                                            "wrong runtimeId, runtimeId=" + runtimeId);
                                }
                            });
                });
    }

    Uni<String> createApiToken(final Long userId, final String password) {
        final var createTokenRequest = new CreateTokenRequest(userId, password);
        return userModule.getUserService().createToken(createTokenRequest)
                .map(CreateTokenResponse::getRawToken);
    }

    Uni<RuntimeModel> getRuntime(final Long id) {
        final var request = new GetRuntimeRequest(id);
        return runtimeModule.getRuntimeService().getRuntime(request)
                .map(GetRuntimeResponse::getRuntime);
    }
}
