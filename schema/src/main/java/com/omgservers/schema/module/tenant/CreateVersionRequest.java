package com.omgservers.schema.module.tenant;

import com.omgservers.schema.module.ShardedRequest;
import com.omgservers.schema.model.version.VersionModel;
import com.omgservers.schema.module.ShardedRequest;
import com.omgservers.schema.model.version.VersionModel;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateVersionRequest implements ShardedRequest {

    @NotNull
    VersionModel version;

    @Override
    public String getRequestShardKey() {
        return version.getId().toString();
    }
}
