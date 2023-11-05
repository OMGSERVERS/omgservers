package com.omgservers.service.module.matchmaker.impl.mappers;

import com.omgservers.model.matchmaker.MatchmakerModel;
import io.vertx.mutiny.sqlclient.Row;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
public class MatchmakerModelMapper {

    public MatchmakerModel fromRow(Row row) {
        final var matchmakerModel = new MatchmakerModel();
        matchmakerModel.setId(row.getLong("id"));
        matchmakerModel.setCreated(row.getOffsetDateTime("created").toInstant());
        matchmakerModel.setModified(row.getOffsetDateTime("modified").toInstant());
        matchmakerModel.setTenantId(row.getLong("tenant_id"));
        matchmakerModel.setVersionId(row.getLong("version_id"));
        matchmakerModel.setDeleted(row.getBoolean("deleted"));
        return matchmakerModel;
    }
}
