package org.ga4gh.registry.util.serialize.sets;

import org.ga4gh.registry.util.serialize.modules.ImplementationSerializerModule;
import org.ga4gh.registry.util.serialize.modules.OrganizationSerializerModule;

public class OrganizationDeepSerializerModuleSet extends AbstractSerializerModuleSet{

    public OrganizationDeepSerializerModuleSet() {
        super();
        addModule(new OrganizationSerializerModule());
        addModule(new ImplementationSerializerModule());
        registerModules();
    }
}