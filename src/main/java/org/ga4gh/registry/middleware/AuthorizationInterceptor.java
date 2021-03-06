package org.ga4gh.registry.middleware;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.MediaType;
import org.ga4gh.registry.AppConfigConstants;
import org.ga4gh.registry.constant.HttpStatusCode;
import org.ga4gh.registry.constant.HttpStatusName;
import org.ga4gh.registry.exception.ForbiddenException;
import org.ga4gh.registry.model.RegistryError;
import org.ga4gh.registry.util.auth.PlaceholderAuth;
import org.ga4gh.registry.util.serialize.RegistrySerializerModule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.servlet.HandlerInterceptor;

public class AuthorizationInterceptor implements HandlerInterceptor {

    @Autowired
    PlaceholderAuth placeholderAuth;

    @Autowired
    @Qualifier(AppConfigConstants.BASIC_REGISTRY_ERROR_SERIALIZER_MODULE)
    RegistrySerializerModule serializerModule;

    public void setRegistryErrorResponseFromException(HttpServletRequest request, HttpServletResponse response, String code, Exception ex) throws Exception {
        RegistryError registryError = new RegistryError();
        registryError.setTimestamp(new Date());
        registryError.setStatus(Integer.parseInt(code));
        registryError.setError(HttpStatusName.get(code));
        registryError.setMessage(ex.getMessage());
        registryError.setPath(request.getServletPath());
        response.reset();
        response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
        response.setStatus(Integer.parseInt(code));
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(serializerModule);
        String serialized = mapper.writeValueAsString(registryError);
        response.getWriter().write(serialized);
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws ForbiddenException, Exception {
        boolean ok = true;
        if (!request.getMethod().equals("GET")) {
            try {
                String authHeader = request.getHeader("authorization");
                placeholderAuth.authorize(authHeader);
            } catch (Exception e) {
                ok = false;
                setRegistryErrorResponseFromException(request, response, HttpStatusCode.FORBIDDEN, e);
            }
        }
        return ok;
    }
}
