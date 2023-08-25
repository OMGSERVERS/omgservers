package com.omgservers.base.impl.operation.getServersOperation;

import io.smallrye.mutiny.Uni;

import java.net.URI;
import java.util.List;

public interface GetServersOperation {

    Uni<List<URI>> getServers();
}
