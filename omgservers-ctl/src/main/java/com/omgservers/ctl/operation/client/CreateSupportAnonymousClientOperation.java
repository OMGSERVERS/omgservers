package com.omgservers.ctl.operation.client;

import com.omgservers.ctl.client.SupportClient;

import java.net.URI;

public interface CreateSupportAnonymousClientOperation {
    SupportClient execute(URI uri);
}
