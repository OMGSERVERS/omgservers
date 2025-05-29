package com.omgservers.ctl.operation.initializer;

import com.omgservers.ctl.client.SupportClient;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
class CreateInitializerOperationImpl implements CreateInitializerOperation {

    @Override
    public Initializer execute(final SupportClient supportClient) {
        return new Initializer(supportClient);
    }
}
