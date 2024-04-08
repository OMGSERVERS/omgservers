package com.omgservers.testDataFactory;

import com.omgservers.model.dto.root.SyncRootRequest;
import com.omgservers.model.root.RootModel;
import com.omgservers.service.factory.root.RootModelFactory;
import com.omgservers.service.module.root.impl.service.rootService.testInterface.RootServiceTestInterface;
import com.omgservers.service.operation.getConfig.GetConfigOperation;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
public class RootTestDataFactory {

    final RootServiceTestInterface rootService;

    final GetConfigOperation getConfigOperation;

    final RootModelFactory rootModelFactory;

    public RootModel createRoot() {
        final var rootId = getConfigOperation.getServiceConfig().bootstrap().rootId();
        final var root = rootModelFactory.create(rootId);
        final var syncRootRequest = new SyncRootRequest(root);
        rootService.syncRoot(syncRootRequest);

        return root;
    }
}
