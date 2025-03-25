package com.omgservers.service.service.cache.impl.service.inmemory;

import com.omgservers.service.service.cache.CacheService;
import com.omgservers.service.service.cache.dto.*;
import com.omgservers.service.service.cache.impl.service.inmemory.method.GetClientLastActivityMethod;
import com.omgservers.service.service.cache.impl.service.inmemory.method.GetRuntimeLastActivityMethod;
import com.omgservers.service.service.cache.impl.service.inmemory.method.SetClientLastActivityMethod;
import com.omgservers.service.service.cache.impl.service.inmemory.method.SetRuntimeLastActivityMethod;
import com.omgservers.service.service.cache.impl.service.inmemory.method.GetClientsLastActivitiesMethod;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class CacheServiceInMemoryImpl implements CacheService {

    final GetClientsLastActivitiesMethod getClientsLastActivitiesMethod;
    final GetRuntimeLastActivityMethod getRuntimeLastActivityMethod;
    final SetRuntimeLastActivityMethod setRuntimeLastActivityMethod;
    final GetClientLastActivityMethod getClientLastActivityMethod;
    final SetClientLastActivityMethod setClientLastActivityMethod;

    @Override
    public Uni<GetRuntimeLastActivityResponse> execute(@Valid final GetRuntimeLastActivityRequest request) {
        return getRuntimeLastActivityMethod.execute(request);
    }

    @Override
    public Uni<SetRuntimeLastActivityResponse> execute(@Valid final SetRuntimeLastActivityRequest request) {
        return setRuntimeLastActivityMethod.execute(request);
    }

    @Override
    public Uni<GetClientLastActivityResponse> execute(@Valid final GetClientLastActivityRequest request) {
        return getClientLastActivityMethod.execute(request);
    }

    @Override
    public Uni<GetClientsLastActivitiesResponse> execute(@Valid final GetClientsLastActivitiesRequest request) {
        return getClientsLastActivitiesMethod.execute(request);
    }

    @Override
    public Uni<SetClientLastActivityResponse> execute(@Valid final SetClientLastActivityRequest request) {
        return setClientLastActivityMethod.execute(request);
    }
}
