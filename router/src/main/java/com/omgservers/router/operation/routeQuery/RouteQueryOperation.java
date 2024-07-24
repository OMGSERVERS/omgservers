package com.omgservers.router.operation.routeQuery;

import java.net.URI;
import java.util.Optional;

public interface RouteQueryOperation {
    Optional<URI> routeQuery(final String query);
}
