package com.omgservers.service.module.user.impl.service.tokenService.impl;

import com.omgservers.model.dto.user.CreateTokenRequest;
import com.omgservers.model.dto.user.CreateTokenResponse;
import com.omgservers.model.dto.user.IntrospectTokenRequest;
import com.omgservers.model.dto.user.IntrospectTokenResponse;
import com.omgservers.service.module.user.impl.operation.decodeToken.DecodeTokenOperation;
import com.omgservers.service.module.user.impl.operation.getUserModuleClient.GetUserModuleClientOperation;
import com.omgservers.service.module.user.impl.operation.getUserModuleClient.UserModuleClient;
import com.omgservers.service.module.user.impl.service.tokenService.TokenService;
import com.omgservers.service.module.user.impl.service.tokenService.impl.method.createToken.CreateTokenMethod;
import com.omgservers.service.module.user.impl.service.tokenService.impl.method.introspectToken.IntrospectTokenMethod;
import com.omgservers.service.operation.calculateShard.CalculateShardOperation;
import com.omgservers.service.operation.handleInternalRequest.HandleInternalRequestOperation;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
class TokenServiceImpl implements TokenService {

    final IntrospectTokenMethod introspectTokenMethod;
    final CreateTokenMethod createTokenMethod;

    final GetUserModuleClientOperation getUserModuleClientOperation;
    final HandleInternalRequestOperation handleInternalRequestOperation;
    final CalculateShardOperation calculateShardOperation;
    final DecodeTokenOperation decodeTokenOperation;

    @Override
    public Uni<CreateTokenResponse> createToken(@Valid final CreateTokenRequest request) {
        return handleInternalRequestOperation.handleInternalRequest(log, request,
                getUserModuleClientOperation::getClient,
                UserModuleClient::createToken,
                createTokenMethod::createToken);
    }

    @Override
    public Uni<IntrospectTokenResponse> introspectToken(@Valid final IntrospectTokenRequest request) {
        final var rawToken = request.getRawToken();
        return Uni.createFrom().item(rawToken)
                .map(decodeTokenOperation::decodeToken)
                .flatMap(tokenObject -> {
                    final var userId = tokenObject.getUserId();
                    final var shardKey = userId.toString();
                    return calculateShardOperation.calculateShard(shardKey)
                            .flatMap(shard -> {
                                if (shard.foreign()) {
                                    return getUserModuleClientOperation.getClient(shard.serverUri())
                                            .introspectToken(request);
                                } else {
                                    return introspectTokenMethod.introspectToken(request);
                                }
                            });
                });
    }
}