package com.omgservers.service.service.job.impl.mapper;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.omgservers.schema.model.job.JobModel;
import com.omgservers.schema.model.job.JobQualifierEnum;
import io.vertx.mutiny.sqlclient.Row;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
public class JobModelMapper {

    final ObjectMapper objectMapper;

    public JobModel fromRow(final Row row) {
        final var job = new JobModel();
        job.setId(row.getLong("id"));
        job.setIdempotencyKey(row.getString("idempotency_key"));
        job.setCreated(row.getOffsetDateTime("created").toInstant());
        job.setModified(row.getOffsetDateTime("modified").toInstant());
        job.setQualifier(JobQualifierEnum.valueOf(row.getString("qualifier")));
        job.setShardKey(row.getLong("shard_key"));
        job.setEntityId(row.getLong("entity_id"));
        job.setDeleted(row.getBoolean("deleted"));
        return job;
    }
}
