package com.omgservers.service.service.bootstrap.impl.method.bootstrapAdminUser;

import io.smallrye.mutiny.Uni;

public interface BootstrapAdminUserMethod {
    Uni<Void> bootstrapAdminUser();
}
