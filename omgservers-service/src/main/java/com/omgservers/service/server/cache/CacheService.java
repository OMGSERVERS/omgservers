package com.omgservers.service.server.cache;

import com.omgservers.service.server.cache.dto.GetClientLastActivityRequest;
import com.omgservers.service.server.cache.dto.GetClientLastActivityResponse;
import com.omgservers.service.server.cache.dto.GetClientsLastActivitiesRequest;
import com.omgservers.service.server.cache.dto.GetClientsLastActivitiesResponse;
import com.omgservers.service.server.cache.dto.GetRuntimeLastActivityRequest;
import com.omgservers.service.server.cache.dto.GetRuntimeLastActivityResponse;
import com.omgservers.service.server.cache.dto.SetClientLastActivityRequest;
import com.omgservers.service.server.cache.dto.SetClientLastActivityResponse;
import com.omgservers.service.server.cache.dto.SetRuntimeLastActivityRequest;
import com.omgservers.service.server.cache.dto.SetRuntimeLastActivityResponse;
import io.smallrye.mutiny.Uni;
import jakarta.validation.Valid;

public interface CacheService {

    Uni<GetRuntimeLastActivityResponse> execute(@Valid GetRuntimeLastActivityRequest request);

    Uni<SetRuntimeLastActivityResponse> execute(@Valid SetRuntimeLastActivityRequest request);

    Uni<GetClientLastActivityResponse> execute(@Valid GetClientLastActivityRequest request);

    Uni<GetClientsLastActivitiesResponse> execute(@Valid GetClientsLastActivitiesRequest request);

    Uni<SetClientLastActivityResponse> execute(@Valid SetClientLastActivityRequest request);
}
