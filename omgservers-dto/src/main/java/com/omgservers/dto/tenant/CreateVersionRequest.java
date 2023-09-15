package com.omgservers.dto.tenant;

import com.omgservers.dto.ShardedRequest;
import com.omgservers.model.version.VersionModel;
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
