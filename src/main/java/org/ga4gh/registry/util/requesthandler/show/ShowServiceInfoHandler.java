package org.ga4gh.registry.util.requesthandler.show;

import java.util.HashMap;

import org.ga4gh.registry.constant.Ids;
import org.ga4gh.registry.model.Implementation;
import org.ga4gh.registry.util.serialize.sets.SerializerModuleSet;


public class ShowServiceInfoHandler extends ShowRequestHandler<Implementation> {

    public ShowServiceInfoHandler(Class<Implementation> responseClass, SerializerModuleSet serializerModuleSet, String idPathParameterName) {
        super(responseClass, serializerModuleSet, idPathParameterName);
        setRequestVariablesA(new HashMap<String, String>());
        getRequestVariablesA().put(idPathParameterName, Ids.SELF_UUID);
    }
}