package com.omgservers.application.module.userModule.impl.service.tokenInternalService.impl;

import com.omgservers.application.module.userModule.impl.operation.getUserServiceApiClientOperation.GetUserServiceApiClientOperation;
import com.omgservers.application.module.userModule.impl.operation.getUserServiceApiClientOperation.UserServiceApiClient;
import com.omgservers.application.module.userModule.impl.service.tokenInternalService.TokenInternalService;
import com.omgservers.application.module.userModule.impl.service.tokenInternalService.impl.method.createTokenMethod.CreateTokenMethod;
import com.omgservers.application.module.userModule.impl.service.tokenInternalService.impl.method.introspectTokenMethod.IntrospectTokenMethod;
import com.omgservers.application.module.userModule.impl.operation.decodeTokenOperation.DecodeTokenOperation;
import com.omgservers.application.operation.calculateShardOperation.CalculateShardOperation;
import com.omgservers.application.module.userModule.impl.service.tokenInternalService.request.CreateTokenInternalRequest;
import com.omgservers.application.module.userModule.impl.service.tokenInternalService.request.IntrospectTokenInternalRequest;
import com.omgservers.application.module.userModule.impl.service.tokenInternalService.response.CreateTokenInternalResponse;
import com.omgservers.application.module.userModule.impl.service.tokenInternalService.response.IntrospectTokenInternalResponse;
import com.omgservers.application.operation.handleInternalRequestOperation.HandleInternalRequestOperation;
import io.smallrye.mutiny.Uni;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import jakarta.enterprise.context.ApplicationScoped;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
class TokenInternalServiceImpl implements TokenInternalService {

    final IntrospectTokenMethod introspectTokenMethod;
    final CreateTokenMethod createTokenMethod;

    final GetUserServiceApiClientOperation getUserServiceApiClientOperation;
    final HandleInternalRequestOperation handleInternalRequestOperation;
    final CalculateShardOperation calculateShardOperation;
    final DecodeTokenOperation decodeTokenOperation;

    @Override
    public Uni<CreateTokenInternalResponse> createToken(final CreateTokenInternalRequest request) {
        return handleInternalRequestOperation.handleInternalRequest(log, request,
                CreateTokenInternalRequest::validate,
                getUserServiceApiClientOperation::getClient,
                UserServiceApiClient::createToken,
                createTokenMethod::createToken);
    }

    @Override
    public Uni<IntrospectTokenInternalResponse> introspectToken(final IntrospectTokenInternalRequest request) {
        IntrospectTokenInternalRequest.validate(request);

        final var rawToken = request.getRawToken();
        return Uni.createFrom().item(rawToken)
                .map(decodeTokenOperation::decodeToken)
                .flatMap(tokenObject -> {
                    final var user = tokenObject.getUserId();
                    final var shardKey = user.toString();
                    return calculateShardOperation.calculateShard(shardKey)
                            .flatMap(shard -> {
                                if (shard.foreign()) {
                                    log.info("Request will be routed, request={}, shard={}", request, shard);
                                    return getUserServiceApiClientOperation.getClient(shard.serverUri())
                                            .introspectToken(request);
                                } else {
                                    return introspectTokenMethod.introspectToken(request);
                                }
                            });
                });
    }
}