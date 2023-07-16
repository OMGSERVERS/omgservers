package com.omgservers.application.module.tenantModule.impl.operation.insertProjectOperation;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.omgservers.application.operation.prepareShardSqlOperation.PrepareShardSqlOperation;
import com.omgservers.application.module.tenantModule.model.project.ProjectModel;
import com.omgservers.application.exception.ServerSideBadRequestException;
import com.omgservers.application.exception.ServerSideConflictException;
import com.omgservers.application.exception.ServerSideInternalException;
import com.omgservers.application.exception.ServerSideNotFoundException;
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
class InsertProjectOperationImpl implements InsertProjectOperation {

    static private final String sql = """
            insert into $schema.tab_tenant_project(tenant_uuid, created, modified, uuid, owner, config)
            values($1, $2, $3, $4, $5, $6)
            """;

    final PrepareShardSqlOperation prepareShardSqlOperation;
    final ObjectMapper objectMapper;

    @Override
    public Uni<Void> insertProject(final SqlConnection sqlConnection,
                                   final int shard,
                                   final ProjectModel project) {
        if (sqlConnection == null) {
            throw new ServerSideBadRequestException("sqlConnection is null");
        }
        if (project == null) {
            throw new ServerSideBadRequestException("project is null");
        }

        return insertQuery(sqlConnection, shard, project)
                .invoke(voidItem -> log.info("Project was inserted, shard={}, project={}", shard, project))
                .onFailure(PgException.class)
                .transform(t -> {
                    final var pgException = (PgException) t;
                    final var code = pgException.getCode();
                    final var column = pgException.getColumn();
                    if (code.equals("23503")) {
                        // foreign_key_violation
                        return new ServerSideNotFoundException("tenant was not found, uuid=" + project.getTenant());
                    } else {
                        return new ServerSideConflictException("unhandled PgException, " + t.getMessage());
                    }
                });
    }

    Uni<Void> insertQuery(SqlConnection sqlConnection, int shard, ProjectModel project) {
        try {
            var preparedSql = prepareShardSqlOperation.prepareShardSql(sql, shard);
            var configString = objectMapper.writeValueAsString(project.getConfig());
            return sqlConnection.preparedQuery(preparedSql)
                    .execute(Tuple.from(new ArrayList<>() {{
                        add(project.getTenant());
                        add(project.getCreated().atOffset(ZoneOffset.UTC));
                        add(project.getModified().atOffset(ZoneOffset.UTC));
                        add(project.getUuid());
                        add(project.getOwner());
                        add(configString);
                    }}))
                    .replaceWithVoid();
        } catch (IOException e) {
            throw new ServerSideInternalException(e.getMessage(), e);
        }
    }
}
