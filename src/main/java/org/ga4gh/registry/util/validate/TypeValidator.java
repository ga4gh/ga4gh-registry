package org.ga4gh.registry.util.validate;

import org.ga4gh.registry.exception.BadRequestException;
import org.ga4gh.registry.model.ServiceType;

public final class TypeValidator {

    public static void validate(String type) throws BadRequestException {

        if (type != null) {
            try {
                new ServiceType(type);
            } catch (InstantiationError e) {
                throw new BadRequestException(e);
            }
        }
    }
}