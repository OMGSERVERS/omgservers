package com.omgservers.test.operations.bootstrapServerOperation;

import com.omgservers.model.index.IndexConfigModel;

import java.net.URI;
import java.util.Map;

public interface BootstrapServerOperation {
    void bootstrap(URI uri, String indexName, IndexConfigModel indexConfig, Map<String, String> serviceAccounts);
}
