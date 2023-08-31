package com.omgservers.operation.createUserGlobals;

import com.omgservers.operation.enrichLuaGlobals.EnrichLuaGlobalsOperation;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.luaj.vm2.Globals;
import org.luaj.vm2.lib.PackageLib;
import org.luaj.vm2.lib.jse.JseBaseLib;
import org.luaj.vm2.lib.jse.JseMathLib;
import org.luaj.vm2.lib.jse.JseStringLib;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class CreateUserGlobalsOperationImpl implements CreateUserGlobalsOperation {

    final EnrichLuaGlobalsOperation enrichLuaGlobalsOperation;

    @Override
    public Globals createUserGlobals() {
        Globals globals = new Globals();
        globals.load(new JseBaseLib());
        globals.load(new PackageLib());
        globals.load(new JseStringLib());
        globals.load(new JseMathLib());
        return enrichLuaGlobalsOperation.enrichLuaGlobals(globals);
    }
}
