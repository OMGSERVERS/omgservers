package com.omgservers.service.module.matchmaker.impl.service.matchmakerService.impl.method.deleteMatchmaker;

import com.omgservers.model.dto.matchmaker.DeleteMatchmakerResponse;
import com.omgservers.model.dto.matchmaker.DeleteMatchmakerRequest;
import io.smallrye.mutiny.Uni;

import java.time.Duration;

public interface DeleteMatchmakerMethod {
    Uni<DeleteMatchmakerResponse> deleteMatchmaker(DeleteMatchmakerRequest request);

    default DeleteMatchmakerResponse deleteMatchmaker(long timeout, DeleteMatchmakerRequest request) {
        return deleteMatchmaker(request)
                .await().atMost(Duration.ofSeconds(timeout));
    }
}
