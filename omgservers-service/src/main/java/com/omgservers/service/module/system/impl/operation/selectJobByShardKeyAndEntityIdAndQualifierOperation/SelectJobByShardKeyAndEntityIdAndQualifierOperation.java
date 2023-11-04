package com.omgservers.service.module.system.impl.operation.selectJobByShardKeyAndEntityIdAndQualifierOperation;

import com.omgservers.model.job.JobModel;
import com.omgservers.model.job.JobQualifierEnum;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import io.vertx.mutiny.sqlclient.SqlConnection;

import java.time.Duration;

public interface SelectJobByShardKeyAndEntityIdAndQualifierOperation {
    Uni<JobModel> selectJobByShardKeyAndEntityIdAndQualifier(SqlConnection sqlConnection,
                                                             Long shardKey,
                                                             Long entityId,
                                                             JobQualifierEnum qualifier);

    default JobModel selectJobByShardKeyAndEntityIdAndQualifier(long timeout,
                                                                PgPool pgPool,
                                                                Long shardKey,
                                                                Long entityId,
                                                                JobQualifierEnum qualifier) {
        return pgPool.withTransaction(sqlConnection -> selectJobByShardKeyAndEntityIdAndQualifier(
                        sqlConnection,
                        shardKey,
                        entityId,
                        qualifier))
                .await().atMost(Duration.ofSeconds(timeout));
    }
}
