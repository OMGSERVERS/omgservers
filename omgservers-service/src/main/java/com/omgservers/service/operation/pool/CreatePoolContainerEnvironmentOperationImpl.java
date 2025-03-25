package com.omgservers.service.operation.pool;

import com.omgservers.schema.model.poolSeverContainer.PoolContainerEnvironment;
import com.omgservers.schema.model.runtime.RuntimeModel;
import com.omgservers.service.operation.server.GetServiceConfigOperation;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.net.URI;
import java.util.HashMap;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
class CreatePoolContainerEnvironmentOperationImpl implements CreatePoolContainerEnvironmentOperation {

    final GetServiceConfigOperation getServiceConfigOperation;

    @Override
    public Uni<HashMap<PoolContainerEnvironment, String>> execute(final RuntimeModel runtime,
                                                                  final String password) {
        final var environment = new HashMap<PoolContainerEnvironment, String>();
        environment.put(PoolContainerEnvironment.RUNTIME_ID, runtime.getId().toString());
        environment.put(PoolContainerEnvironment.PASSWORD, password);
        environment.put(PoolContainerEnvironment.QUALIFIER, runtime.getQualifier().toString());

        final URI serviceUri;
        if (getServiceConfigOperation.getServiceConfig().runtimes().overriding().enabled()) {
            serviceUri = getServiceConfigOperation.getServiceConfig().runtimes().overriding().uri();
        } else {
            serviceUri = getServiceConfigOperation.getServiceConfig().server().uri();
        }
        environment.put(PoolContainerEnvironment.SERVICE_URL, serviceUri.toString());

        return Uni.createFrom().item(environment);
    }
}
