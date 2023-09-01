package com.omgservers.module.tenant.impl.operation.upsertProject;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.omgservers.exception.ServerSideBadRequestException;
import com.omgservers.exception.ServerSideInternalException;
import com.omgservers.model.project.ProjectModel;
import com.omgservers.operation.prepareShardSql.PrepareShardSqlOperation;
import com.omgservers.operation.transformPgException.TransformPgExceptionOperation;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;
import io.vertx.mutiny.sqlclient.Tuple;
import io.vertx.pgclient.PgException;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.time.ZoneOffset;
import java.util.ArrayList;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class UpsertProjectOperationImpl implements UpsertProjectOperation {

    static private final String sql = """
            insert into $schema.tab_tenant_project(id, tenant_id, created, modified, config)
            values($1, $2, $3, $4, $5)
            on conflict (id) do
            nothing
            """;

    final TransformPgExceptionOperation transformPgExceptionOperation;
    final PrepareShardSqlOperation prepareShardSqlOperation;
    final ObjectMapper objectMapper;

    @Override
    public Uni<Boolean> upsertProject(final SqlConnection sqlConnection,
                                      final int shard,
                                      final ProjectModel project) {
        if (sqlConnection == null) {
            throw new ServerSideBadRequestException("sqlConnection is null");
        }
        if (project == null) {
            throw new ServerSideBadRequestException("project is null");
        }

        return upsertQuery(sqlConnection, shard, project)
                .invoke(inserted -> {
                    if (inserted) {
                        log.info("Project was inserted, shard={}, project={}", shard, project);
                    } else {
                        log.info("Project was updated, shard={}, project={}", shard, project);
                    }
                })
                .onFailure(PgException.class)
                .transform(t -> transformPgExceptionOperation.transformPgException((PgException) t));
    }

    Uni<Boolean> upsertQuery(SqlConnection sqlConnection, int shard, ProjectModel project) {
        try {
            var preparedSql = prepareShardSqlOperation.prepareShardSql(sql, shard);
            var configString = objectMapper.writeValueAsString(project.getConfig());
            return sqlConnection.preparedQuery(preparedSql)
                    .execute(Tuple.from(new ArrayList<>() {{
                        add(project.getId());
                        add(project.getTenantId());
                        add(project.getCreated().atOffset(ZoneOffset.UTC));
                        add(project.getModified().atOffset(ZoneOffset.UTC));
                        add(configString);
                    }}))
                    .map(rowSet -> rowSet.rowCount() > 0);
        } catch (IOException e) {
            throw new ServerSideInternalException(e.getMessage(), e);
        }
    }
}
