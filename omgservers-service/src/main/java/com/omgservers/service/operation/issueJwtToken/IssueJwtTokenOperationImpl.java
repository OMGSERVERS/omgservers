package com.omgservers.service.operation.issueJwtToken;

import com.omgservers.model.internalRole.InternalRoleEnum;
import io.smallrye.jwt.build.Jwt;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.extern.slf4j.Slf4j;

import java.util.Set;

@Slf4j
@ApplicationScoped
class IssueJwtTokenOperationImpl implements IssueJwtTokenOperation {

    // TODO: get from configuration
    private final String ISSUER = "https://omgservers.com";

    @Override
    public String issueAdminJwtToken() {
        String jwtToken = Jwt.issuer(ISSUER)
                .groups(InternalRoleEnum.ADMIN.getName())
                .sign();
        return jwtToken;
    }

    @Override
    public String issueServiceJwtToken() {
        String jwtToken = Jwt.issuer(ISSUER)
                .groups(InternalRoleEnum.SERVICE.getName())
                .sign();
        return jwtToken;
    }

    @Override
    public String issueUserJwtToken(Long userId, Set<String> groups) {
        String jwtToken = Jwt.issuer(ISSUER)
                .upn(userId.toString())
                .groups(groups)
                .sign();
        return jwtToken;
    }
}
