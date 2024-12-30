package com.omgservers.schema.entrypoint.registry;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RequestRecordDto {

    String id;
    String addr;
    String host;
    String method;

    @JsonProperty("useragent")
    String userAgent;
}
