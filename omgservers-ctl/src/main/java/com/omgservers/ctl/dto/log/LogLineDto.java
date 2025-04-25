package com.omgservers.ctl.dto.log;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonDeserialize(using = LogLineDeserializer.class)
public class LogLineDto {

    @JsonProperty("q")
    LogLineQualifierEnum qualifier;

    @JsonProperty("b")
    LogLineBodyDto body;
}
