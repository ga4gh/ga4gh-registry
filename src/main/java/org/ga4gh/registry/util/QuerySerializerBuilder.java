package org.ga4gh.registry.util;

public class QuerySerializerBuilder<T> {

    private Class<T> responseClass;

    public QuerySerializerBuilder(Class<T> responseClass) {
        this.responseClass = responseClass;
    }

    public QuerySerializer<T> build() {
        return new QuerySerializer<>(responseClass);
    }

}