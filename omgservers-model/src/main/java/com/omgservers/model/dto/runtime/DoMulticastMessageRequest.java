package com.omgservers.model.dto.runtime;

import com.omgservers.model.dto.ShardedRequest;
import com.omgservers.model.recipient.Recipient;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DoMulticastMessageRequest implements ShardedRequest {

    @NotNull
    Long runtimeId;

    @NotEmpty
    List<Recipient> recipients;

    @NotNull
    Object message;

    @Override
    public String getRequestShardKey() {
        return runtimeId.toString();
    }
}
