package org.ga4gh.registry.exception;

import java.util.Date;
import java.util.Map;

import org.ga4gh.registry.AppConfigConstants;
import org.ga4gh.registry.util.serialize.serializers.DateSerializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.web.servlet.error.DefaultErrorAttributes;
import org.springframework.web.context.request.WebRequest;

public class CustomErrorAttributes extends DefaultErrorAttributes {

    @Autowired
    @Qualifier(AppConfigConstants.BASIC_DATE_SERIALIZER)
    private DateSerializer dateSerializer;
    
    /*
    @Override
    public Map<String, Object> getErrorAttributes(
      WebRequest webRequest, boolean includeStackTrace) {
        Map<String, Object> errorAttributes = super.getErrorAttributes(webRequest, includeStackTrace);
        errorAttributes.put("timestamp", getDateSerializer().getFormatter().format(new Date()));
        return errorAttributes;
    }
    */

    public void setDateSerializer(DateSerializer dateSerializer) {
        this.dateSerializer = dateSerializer;
    }

    public DateSerializer getDateSerializer() {
        return dateSerializer;
    }
}