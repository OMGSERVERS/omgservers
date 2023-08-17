package com.omgservers.application.module.internalModule.model.log;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LogModel {

    static public void validate(LogModel log) {
        if (log == null) {
            throw new IllegalArgumentException("log is null");
        }
    }

    Long id;
    @ToString.Exclude
    Instant created;
    String message;
}
