package org.ga4gh.registry.util.serialize.sets;

import org.ga4gh.registry.util.serialize.modules.ImplementationSerializerModule;
import org.ga4gh.registry.util.serialize.modules.OrganizationSerializerModule;

public class ImplementationDeepSerializerModuleSet extends AbstractSerializerModuleSet{

    public ImplementationDeepSerializerModuleSet() {
        super();
        addModule(new ImplementationSerializerModule());
        addModule(new OrganizationSerializerModule());
        registerModules();
    }
}