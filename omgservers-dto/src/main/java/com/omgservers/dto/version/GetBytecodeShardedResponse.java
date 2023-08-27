package com.omgservers.dto.version;

import com.omgservers.model.version.VersionBytecodeModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetBytecodeShardedResponse {

    VersionBytecodeModel bytecode;
}
