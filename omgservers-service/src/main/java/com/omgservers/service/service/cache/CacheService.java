package com.omgservers.service.service.cache;

import com.omgservers.service.service.cache.dto.*;
import io.smallrye.mutiny.Uni;
import jakarta.validation.Valid;

public interface CacheService {

    Uni<GetRuntimeLastActivityResponse> execute(@Valid GetRuntimeLastActivityRequest request);

    Uni<SetRuntimeLastActivityResponse> execute(@Valid SetRuntimeLastActivityRequest request);

    Uni<GetClientLastActivityResponse> execute(@Valid GetClientLastActivityRequest request);

    Uni<GetClientsLastActivitiesResponse> execute(@Valid GetClientsLastActivitiesRequest request);

    Uni<SetClientLastActivityResponse> execute(@Valid SetClientLastActivityRequest request);
}
