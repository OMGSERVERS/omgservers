package com.omgservers.schema.entrypoint.registry.handleEvents;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SourceRecordDto {

    String addr;

    @JsonProperty("instanceID")
    String instanceId;
}
