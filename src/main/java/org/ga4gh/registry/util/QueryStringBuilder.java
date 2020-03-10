package org.ga4gh.registry.util;

import java.util.StringJoiner;

public class QueryStringBuilder<T> {

    private Class<T> responseClass;
    private StringBuffer startBuffer;
    private StringBuffer joinBuffer;
    private StringJoiner filterBuffer;

    public QueryStringBuilder(Class<T> responseClass) {
        this.responseClass = responseClass;
        startBuffer = new StringBuffer();
        joinBuffer = new StringBuffer();
        filterBuffer = new StringJoiner(" AND ");
        startBuffer.append("select a from " + this.responseClass.getName() + " a ");
    }

    public void join(String property) {
        joinBuffer.append("JOIN FETCH a." + property + " ");
    }

    public void filter(String property, String value) {
        filterBuffer.add("a." + property + "='" + value + "'");
    }

    public String build() {
        String queryString = startBuffer.toString() + joinBuffer.toString();
        if (filterBuffer.length() > 0) {
            queryString += "WHERE ";
            queryString += filterBuffer.toString();
        }
        return queryString;
    }
}