package com.omgservers.service.operation.docker;

public interface ParseDockerImageOperation {

    ParsedImage execute(String image);
}
