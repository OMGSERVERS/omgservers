package com.omgservers.module.tenant.impl.mapper;

import com.omgservers.model.versionMatchmaker.VersionMatchmakerModel;
import io.vertx.mutiny.sqlclient.Row;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
public class VersionMatchmakerMapper {

    public VersionMatchmakerModel fromRow(Row row) {
        final var stageMatchmaker = new VersionMatchmakerModel();
        stageMatchmaker.setId(row.getLong("id"));
        stageMatchmaker.setTenantId(row.getLong("tenant_id"));
        stageMatchmaker.setVersionId(row.getLong("version_id"));
        stageMatchmaker.setCreated(row.getOffsetDateTime("created").toInstant());
        stageMatchmaker.setModified(row.getOffsetDateTime("modified").toInstant());
        stageMatchmaker.setMatchmakerId(row.getLong("matchmaker_id"));
        stageMatchmaker.setDeleted(row.getBoolean("deleted"));
        return stageMatchmaker;
    }
}
