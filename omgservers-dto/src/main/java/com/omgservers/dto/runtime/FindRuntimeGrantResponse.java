package com.omgservers.dto.runtime;

import com.omgservers.model.runtimeGrant.RuntimeGrantModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FindRuntimeGrantResponse {

    RuntimeGrantModel runtimeGrant;
}
