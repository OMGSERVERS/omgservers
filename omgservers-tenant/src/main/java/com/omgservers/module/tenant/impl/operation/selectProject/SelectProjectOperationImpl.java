package com.omgservers.module.tenant.impl.operation.selectProject;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.omgservers.exception.ServerSideInternalException;
import com.omgservers.exception.ServerSideNotFoundException;
import com.omgservers.model.project.ProjectConfigModel;
import com.omgservers.model.project.ProjectModel;
import com.omgservers.operation.prepareShardSql.PrepareShardSqlOperation;
import com.omgservers.operation.transformPgException.TransformPgExceptionOperation;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.Row;
import io.vertx.mutiny.sqlclient.RowSet;
import io.vertx.mutiny.sqlclient.SqlConnection;
import io.vertx.mutiny.sqlclient.Tuple;
import io.vertx.pgclient.PgException;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class SelectProjectOperationImpl implements SelectProjectOperation {

    static private final String sql = """
            select id, tenant_id, created, modified, config
            from $schema.tab_tenant_project
            where id = $1
            limit 1
            """;

    final TransformPgExceptionOperation transformPgExceptionOperation;
    final PrepareShardSqlOperation prepareShardSqlOperation;

    final ObjectMapper objectMapper;

    @Override
    public Uni<ProjectModel> selectProject(final SqlConnection sqlConnection,
                                           final int shard,
                                           final Long id) {
        if (sqlConnection == null) {
            throw new IllegalArgumentException("sqlConnection is null");
        }
        if (id == null) {
            throw new IllegalArgumentException("uuid is null");
        }

        String preparedSql = prepareShardSqlOperation.prepareShardSql(sql, shard);

        return sqlConnection.preparedQuery(preparedSql)
                .execute(Tuple.of(id))
                .map(RowSet::iterator)
                .map(iterator -> {
                    if (iterator.hasNext()) {
                        try {
                            final var project = createProject(iterator.next());
                            log.info("Project was found, project={}", project);
                            return project;
                        } catch (IOException e) {
                            throw new ServerSideInternalException("project config can't be parsed, id=" + id);
                        }
                    } else {
                        throw new ServerSideNotFoundException("project was not found, id=" + id);
                    }
                })
                .onFailure(PgException.class)
                .transform(t -> transformPgExceptionOperation.transformPgException((PgException) t));
    }

    ProjectModel createProject(Row row) throws IOException {
        ProjectModel project = new ProjectModel();
        project.setId(row.getLong("id"));
        project.setTenantId(row.getLong("tenant_id"));
        project.setCreated(row.getOffsetDateTime("created").toInstant());
        project.setModified(row.getOffsetDateTime("modified").toInstant());
        project.setConfig(objectMapper.readValue(row.getString("config"), ProjectConfigModel.class));
        return project;
    }
}
