package com.omgservers.model.dto.system;

import com.omgservers.model.event.EventStatusEnum;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateEventsStatusRequest {

    @NotNull
    @NotEmpty
    List<Long> ids;

    @NotNull
    EventStatusEnum status;
}
