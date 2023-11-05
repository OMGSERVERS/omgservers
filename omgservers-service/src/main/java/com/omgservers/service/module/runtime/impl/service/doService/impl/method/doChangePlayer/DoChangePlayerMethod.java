package com.omgservers.service.module.runtime.impl.service.doService.impl.method.doChangePlayer;

import com.omgservers.model.dto.runtime.DoChangePlayerRequest;
import com.omgservers.model.dto.runtime.DoChangePlayerResponse;
import io.smallrye.mutiny.Uni;

public interface DoChangePlayerMethod {
    Uni<DoChangePlayerResponse> doChangePlayer(DoChangePlayerRequest request);
}
