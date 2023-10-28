package com.omgservers.module.script.impl.operation.handleLuaCallOperation;

import io.smallrye.mutiny.Uni;
import org.luaj.vm2.Varargs;

import java.util.function.Supplier;

public interface HandleLuaCallOperation {
    Varargs handleLuaCall(Supplier<Uni<Varargs>> supplier);
}
