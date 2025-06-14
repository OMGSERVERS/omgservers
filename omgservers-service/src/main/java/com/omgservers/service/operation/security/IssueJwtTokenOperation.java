package com.omgservers.service.operation.security;

import com.omgservers.schema.model.user.UserRoleEnum;

import java.util.Set;

public interface IssueJwtTokenOperation {
    String issueServiceJwtToken();

    String issueUserJwtToken(Long userId, Set<String> groups);

    String issueRuntimeJwtToken(Long runtimeId);

    String issueConnectorClientWsToken(Long clientId);

    String issueDispatcherClientWsToken(Long subject, Long runtimeId, UserRoleEnum role);
}
