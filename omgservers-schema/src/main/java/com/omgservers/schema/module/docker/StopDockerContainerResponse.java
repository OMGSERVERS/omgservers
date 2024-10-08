package com.omgservers.schema.module.docker;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StopDockerContainerResponse {

    Boolean stopped;
}
