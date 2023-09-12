package com.omgservers.module.script.impl.operation.getScriptModuleClient;

import java.net.URI;

public interface GetScriptModuleClientOperation {
    ScriptModuleClient getClient(URI uri);

    Boolean hasCacheFor(URI uri);

    Integer sizeOfCache();
}
