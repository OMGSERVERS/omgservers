package com.omgservers.application.module.luaModule.impl.operation.createUserGlobalsOperation;

import lombok.extern.slf4j.Slf4j;
import org.luaj.vm2.Globals;
import org.luaj.vm2.lib.PackageLib;
import org.luaj.vm2.lib.jse.JseBaseLib;
import org.luaj.vm2.lib.jse.JseMathLib;
import org.luaj.vm2.lib.jse.JseStringLib;

import jakarta.enterprise.context.ApplicationScoped;

@Slf4j
@ApplicationScoped
class CreateUserGlobalsOperationImpl implements CreateUserGlobalsOperation {

    @Override
    public Globals createUserGlobals() {
        Globals globals = new Globals();
        globals.load(new JseBaseLib());
        globals.load(new PackageLib());
        globals.load(new JseStringLib());
        globals.load(new JseMathLib());
        return globals;
    }
}
