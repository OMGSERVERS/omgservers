package com.omgservers.service.shard.user.impl.operation.getUserModuleClient;

import java.net.URI;

public interface GetUserModuleClientOperation {
    UserModuleClient execute(URI uri);
}
