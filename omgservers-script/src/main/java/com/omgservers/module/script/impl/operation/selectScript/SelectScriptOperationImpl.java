package com.omgservers.module.script.impl.operation.selectScript;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.omgservers.exception.ServerSideConflictException;
import com.omgservers.model.script.ScriptConfigModel;
import com.omgservers.model.script.ScriptModel;
import com.omgservers.model.script.ScriptTypeEnum;
import com.omgservers.operation.selectObject.SelectObjectOperation;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.Row;
import io.vertx.mutiny.sqlclient.SqlConnection;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.Collections;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class SelectScriptOperationImpl implements SelectScriptOperation {

    final SelectObjectOperation selectObjectOperation;

    final ObjectMapper objectMapper;

    @Override
    public Uni<ScriptModel> selectScript(final SqlConnection sqlConnection,
                                         final int shard,
                                         final Long id) {
        return selectObjectOperation.selectObject(
                sqlConnection,
                shard,
                """
                        select id, created, modified, tenant_id, version_id, type, state, config
                        from $schema.tab_script
                        where id = $1
                        limit 1
                        """,
                Collections.singletonList(id),
                "Script",
                this::createScript);
    }

    ScriptModel createScript(Row row) {
        ScriptModel script = new ScriptModel();
        script.setId(row.getLong("id"));
        script.setCreated(row.getOffsetDateTime("created").toInstant());
        script.setModified(row.getOffsetDateTime("modified").toInstant());
        script.setTenantId(row.getLong("tenant_id"));
        script.setVersionId(row.getLong("version_id"));
        script.setType(ScriptTypeEnum.valueOf(row.getString("type")));
        script.setState(row.getString("state"));
        try {
            script.setConfig(objectMapper.readValue(row.getString("config"), ScriptConfigModel.class));
        } catch (IOException e) {
            throw new ServerSideConflictException("script config can't be parsed, script=" + script, e);
        }
        return script;
    }
}
