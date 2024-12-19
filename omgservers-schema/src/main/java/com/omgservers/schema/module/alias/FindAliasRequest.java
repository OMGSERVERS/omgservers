package com.omgservers.schema.module.alias;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.omgservers.schema.module.ShardedRequest;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FindAliasRequest implements ShardedRequest {

    @NotNull
    Long shardKey;

    Long entityId;

    String value;

    @Override
    public String getRequestShardKey() {
        return shardKey.toString();
    }

    @AssertTrue
    @JsonIgnore
    boolean valid() {
        return Objects.nonNull(entityId) || Objects.nonNull(value) && !value.isBlank();
    }
}
