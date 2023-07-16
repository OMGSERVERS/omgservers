package com.omgservers.platforms.integrationtest.operations.bootstrapServerOperation;

import com.omgservers.application.module.internalModule.model.index.IndexModel;

import java.net.URI;
import java.util.Map;

public interface BootstrapServerOperation {
    void bootstrap(URI uri, IndexModel index, Map<String, String> serviceAccounts);
}
