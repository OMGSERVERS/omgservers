package com.omgservers.service.operation.pool;

import com.omgservers.schema.model.poolContainer.PoolContainerEnvironmentEnum;
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
    public Uni<HashMap<PoolContainerEnvironmentEnum, String>> execute(final RuntimeModel runtime,
                                                                      final String password) {
        final var environment = new HashMap<PoolContainerEnvironmentEnum, String>();
        environment.put(PoolContainerEnvironmentEnum.RUNTIME_ID, runtime.getId().toString());
        environment.put(PoolContainerEnvironmentEnum.PASSWORD, password);
        environment.put(PoolContainerEnvironmentEnum.QUALIFIER, runtime.getQualifier().toString());

        final URI serviceUri;
        if (getServiceConfigOperation.getServiceConfig().runtimes().overriding().enabled()) {
            serviceUri = getServiceConfigOperation.getServiceConfig().runtimes().overriding().uri();
        } else {
            serviceUri = getServiceConfigOperation.getServiceConfig().server().uri();
        }
        environment.put(PoolContainerEnvironmentEnum.SERVICE_URL, serviceUri.toString());

        return Uni.createFrom().item(environment);
    }
}
