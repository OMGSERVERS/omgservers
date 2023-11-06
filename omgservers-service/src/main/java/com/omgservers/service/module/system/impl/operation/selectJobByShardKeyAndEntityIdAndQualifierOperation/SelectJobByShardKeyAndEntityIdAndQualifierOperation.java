package com.omgservers.service.module.system.impl.operation.selectJobByShardKeyAndEntityIdAndQualifierOperation;

import com.omgservers.model.job.JobModel;
import com.omgservers.model.job.JobQualifierEnum;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;

public interface SelectJobByShardKeyAndEntityIdAndQualifierOperation {
    Uni<JobModel> selectJobByShardKeyAndEntityIdAndQualifier(SqlConnection sqlConnection,
                                                             Long shardKey,
                                                             Long entityId,
                                                             JobQualifierEnum qualifier);
}
