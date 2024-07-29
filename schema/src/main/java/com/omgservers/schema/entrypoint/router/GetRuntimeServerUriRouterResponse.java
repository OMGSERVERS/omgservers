package com.omgservers.schema.entrypoint.router;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.net.URI;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetRuntimeServerUriRouterResponse {

    URI serverUri;
}
