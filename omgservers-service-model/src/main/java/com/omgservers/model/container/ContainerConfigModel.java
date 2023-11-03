package com.omgservers.model.container;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ContainerConfigModel {

    @NotNull
    Map<String, String> environment;
}
