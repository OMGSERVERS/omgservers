package com.omgservers.ctl.operation.client;

import com.omgservers.ctl.client.SupportClient;

import java.net.URI;

public interface CreateSupportClientOperation {
    SupportClient execute(URI uri, String bearerToken);
}
