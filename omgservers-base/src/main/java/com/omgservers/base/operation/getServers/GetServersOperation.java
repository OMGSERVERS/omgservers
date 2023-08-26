package com.omgservers.base.operation.getServers;

import io.smallrye.mutiny.Uni;

import java.net.URI;
import java.util.List;

public interface GetServersOperation {

    Uni<List<URI>> getServers();
}
