package com.omgservers.schema.module.pool.docker;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StartDockerContainerResponse {

    Boolean started;
}
