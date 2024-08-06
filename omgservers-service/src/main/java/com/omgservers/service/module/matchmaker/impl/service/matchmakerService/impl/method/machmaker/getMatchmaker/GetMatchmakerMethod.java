package com.omgservers.service.module.matchmaker.impl.service.matchmakerService.impl.method.machmaker.getMatchmaker;

import com.omgservers.schema.module.matchmaker.GetMatchmakerRequest;
import com.omgservers.schema.module.matchmaker.GetMatchmakerResponse;
import io.smallrye.mutiny.Uni;

public interface GetMatchmakerMethod {
    Uni<GetMatchmakerResponse> getMatchmaker(GetMatchmakerRequest request);
}
