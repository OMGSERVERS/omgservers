package com.omgservers.testDataFactory;

import com.omgservers.schema.model.root.RootModel;
import com.omgservers.schema.module.root.root.GetRootRequest;
import com.omgservers.schema.module.root.root.SyncRootRequest;
import com.omgservers.service.exception.ServerSideNotFoundException;
import com.omgservers.service.factory.root.RootModelFactory;
import com.omgservers.service.shard.root.service.testInterface.RootServiceTestInterface;
import com.omgservers.service.operation.server.GenerateIdOperation;
import com.omgservers.service.operation.server.GetServiceConfigOperation;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
public class RootTestDataFactory {

    final RootServiceTestInterface rootService;

    final GenerateIdOperation generateIdOperation;
    final GetServiceConfigOperation getServiceConfigOperation;

    final RootModelFactory rootModelFactory;

    public RootModel createRoot() {
        final var rootId = generateIdOperation.generateId();

        try {
            final var getRootRequest = new GetRootRequest(rootId);
            log.info("Root was already created, rootId={}", rootId);
            return rootService.getRoot(getRootRequest).getRoot();
        } catch (ServerSideNotFoundException e) {
            final var root = rootModelFactory.create(rootId);
            final var syncRootRequest = new SyncRootRequest(root);
            rootService.syncRoot(syncRootRequest);
            return root;
        }
    }
}
