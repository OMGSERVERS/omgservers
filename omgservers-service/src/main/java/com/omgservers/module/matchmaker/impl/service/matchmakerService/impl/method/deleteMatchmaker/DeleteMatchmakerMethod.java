package com.omgservers.module.matchmaker.impl.service.matchmakerService.impl.method.deleteMatchmaker;

import com.omgservers.dto.matchmaker.DeleteMatchmakerResponse;
import com.omgservers.dto.matchmaker.DeleteMatchmakerRequest;
import io.smallrye.mutiny.Uni;

import java.time.Duration;

public interface DeleteMatchmakerMethod {
    Uni<DeleteMatchmakerResponse> deleteMatchmaker(DeleteMatchmakerRequest request);

    default DeleteMatchmakerResponse deleteMatchmaker(long timeout, DeleteMatchmakerRequest request) {
        return deleteMatchmaker(request)
                .await().atMost(Duration.ofSeconds(timeout));
    }
}
