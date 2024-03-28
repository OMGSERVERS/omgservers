package com.omgservers.model.serverContainer;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ServerContainerConfigModel {

    @NotNull
    Map<String, String> environment;
}
