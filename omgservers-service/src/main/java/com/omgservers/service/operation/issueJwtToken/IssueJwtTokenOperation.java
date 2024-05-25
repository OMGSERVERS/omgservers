package com.omgservers.service.operation.issueJwtToken;

import java.util.Set;

public interface IssueJwtTokenOperation {
    String issueAdminJwtToken();

    String issueServiceJwtToken();

    String issueUserJwtToken(Long userId, Set<String> groups);
}
