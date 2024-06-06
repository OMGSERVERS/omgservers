package com.omgservers.service.module.system.impl.service.bootstrapService.impl.method.bootstrapAdminUser;

import io.smallrye.mutiny.Uni;

public interface BootstrapAdminUserMethod {
    Uni<Void> bootstrapAdminUser();
}
