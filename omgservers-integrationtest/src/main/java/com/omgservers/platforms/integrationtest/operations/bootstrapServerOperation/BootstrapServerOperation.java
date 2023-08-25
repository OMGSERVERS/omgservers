package com.omgservers.platforms.integrationtest.operations.bootstrapServerOperation;

import com.omgservers.model.index.IndexConfigModel;
import com.omgservers.model.index.IndexModel;

import java.net.URI;
import java.util.Map;

public interface BootstrapServerOperation {
    void bootstrap(URI uri, String indexName, IndexConfigModel indexConfig, Map<String, String> serviceAccounts);
}
