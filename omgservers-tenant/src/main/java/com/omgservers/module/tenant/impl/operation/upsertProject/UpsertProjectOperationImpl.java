package com.omgservers.module.tenant.impl.operation.upsertProject;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.omgservers.operation.prepareShardSql.PrepareShardSqlOperation;
import com.omgservers.exception.ServerSideBadRequestException;
import com.omgservers.exception.ServerSideConflictException;
import com.omgservers.exception.ServerSideInternalException;
import com.omgservers.exception.ServerSideNotFoundException;
import com.omgservers.model.project.ProjectModel;
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
            insert into $schema.tab_tenant_project(id, tenant_id, created, modified, owner_id, config)
            values($1, $2, $3, $4, $5, $6)
            on conflict (id) do
            update set modified = $4, owner_id = $5, config = $6
            returning xmax::text::int = 0 as inserted
            """;

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
                .transform(t -> {
                    final var pgException = (PgException) t;
                    final var code = pgException.getSqlState();
                    if (code.equals("23503")) {
                        // foreign_key_violation
                        return new ServerSideNotFoundException("tenant was not found, name=" + project.getTenantId());
                    } else {
                        return new ServerSideConflictException("unhandled PgException, " + t.getMessage());
                    }
                });
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
                        add(project.getOwnerId());
                        add(configString);
                    }}))
                    .map(rowSet -> rowSet.iterator().next().getBoolean("inserted"));
        } catch (IOException e) {
            throw new ServerSideInternalException(e.getMessage(), e);
        }
    }
}
