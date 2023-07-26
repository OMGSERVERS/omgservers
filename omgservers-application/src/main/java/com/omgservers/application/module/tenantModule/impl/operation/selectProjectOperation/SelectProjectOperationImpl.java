package com.omgservers.application.module.tenantModule.impl.operation.selectProjectOperation;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.omgservers.application.exception.ServerSideInternalException;
import com.omgservers.application.exception.ServerSideNotFoundException;
import com.omgservers.application.operation.prepareShardSqlOperation.PrepareShardSqlOperation;
import com.omgservers.application.module.tenantModule.model.project.ProjectConfigModel;
import com.omgservers.application.module.tenantModule.model.project.ProjectModel;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.Row;
import io.vertx.mutiny.sqlclient.RowSet;
import io.vertx.mutiny.sqlclient.SqlConnection;
import io.vertx.mutiny.sqlclient.Tuple;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import jakarta.enterprise.context.ApplicationScoped;

import java.io.IOException;
import java.util.UUID;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class SelectProjectOperationImpl implements SelectProjectOperation {

    static private final String sql = """
            select id, tenant_id, created, modified, owner_id, config
            from $schema.tab_tenant_project
            where id = $1
            limit 1
            """;

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
                });
    }

    ProjectModel createProject(Row row) throws IOException {
        ProjectModel project = new ProjectModel();
        project.setId(row.getLong("id"));
        project.setTenantId(row.getLong("tenant_id"));
        project.setCreated(row.getOffsetDateTime("created").toInstant());
        project.setModified(row.getOffsetDateTime("modified").toInstant());
        project.setOwnerId(row.getLong("owner_id"));
        project.setConfig(objectMapper.readValue(row.getString("config"), ProjectConfigModel.class));
        return project;
    }
}
