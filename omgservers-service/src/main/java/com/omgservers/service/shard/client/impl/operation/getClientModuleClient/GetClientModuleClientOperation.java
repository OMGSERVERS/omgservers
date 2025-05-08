package com.omgservers.service.shard.client.impl.operation.getClientModuleClient;

import java.net.URI;

public interface GetClientModuleClientOperation {
    ClientModuleClient execute(URI uri);
}
