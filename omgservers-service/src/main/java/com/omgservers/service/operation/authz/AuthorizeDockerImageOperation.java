package com.omgservers.service.operation.authz;

public interface AuthorizeDockerImageOperation {
    DockerImageAuthorization execute(String tenant,
                                     String project,
                                     String image);
}
