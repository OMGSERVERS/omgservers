package com.omgservers.dto.userModule;

import com.omgservers.model.object.ObjectModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetObjectInternalResponse {

    ObjectModel object;
}
