package com.omgservers.test.operations.getDeveloperClientOperation;

import java.net.URI;

public interface GetDeveloperClientOperation {
    DeveloperClientForAnonymousAccess getDeveloperClientForAnonymousAccess(URI uri);

    DeveloperClientForAuthenticatedAccess getDeveloperClientForAuthenticatedAccess(URI uri);
}
