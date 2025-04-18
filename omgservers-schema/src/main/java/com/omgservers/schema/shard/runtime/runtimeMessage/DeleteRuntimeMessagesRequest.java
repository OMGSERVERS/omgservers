package com.omgservers.schema.shard.runtime.runtimeMessage;

import com.omgservers.schema.shard.ShardRequest;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DeleteRuntimeMessagesRequest implements ShardRequest {

    @NotNull
    Long runtimeId;

    @NotEmpty
    List<Long> ids;

    @Override
    public String getRequestShardKey() {
        return runtimeId.toString();
    }
}
