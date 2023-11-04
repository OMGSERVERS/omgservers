package com.omgservers.service.module.system.impl.mappers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.omgservers.model.job.JobModel;
import com.omgservers.model.job.JobQualifierEnum;
import io.vertx.mutiny.sqlclient.Row;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
public class JobModelMapper {

    final ObjectMapper objectMapper;

    public JobModel fromRow(Row row) {
        JobModel job = new JobModel();
        job.setId(row.getLong("id"));
        job.setCreated(row.getOffsetDateTime("created").toInstant());
        job.setShardKey(row.getLong("shard_key"));
        job.setEntityId(row.getLong("entity_id"));
        job.setQualifier(JobQualifierEnum.valueOf(row.getString("qualifier")));
        return job;
    }
}
