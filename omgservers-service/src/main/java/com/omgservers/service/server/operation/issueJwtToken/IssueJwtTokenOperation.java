package com.omgservers.service.server.operation.issueJwtToken;

import com.omgservers.schema.model.user.UserRoleEnum;

import java.util.Set;

public interface IssueJwtTokenOperation {
    String issueServiceJwtToken();

    String issueUserJwtToken(Long userId, Set<String> groups);

    String issueWsJwtToken(Long subject, Long runtimeId, UserRoleEnum role);
}
