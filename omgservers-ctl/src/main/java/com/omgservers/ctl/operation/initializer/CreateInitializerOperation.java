package com.omgservers.ctl.operation.initializer;

import com.omgservers.ctl.client.SupportClient;

public interface CreateInitializerOperation {
    Initializer execute(SupportClient supportClient);
}
