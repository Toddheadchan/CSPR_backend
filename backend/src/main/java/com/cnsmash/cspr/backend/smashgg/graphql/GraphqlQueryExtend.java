package com.cnsmash.cspr.backend.smashgg.graphql;

import org.mountcloud.graphql.request.param.RequestParameter;
import org.mountcloud.graphql.request.query.GraphqlQuery;

public class GraphqlQueryExtend extends GraphqlQuery {
    protected GraphqlQueryExtend(String requestName) {
        super(requestName);
    }

    private String stringValue;

    public RequestParameter addObjectParameter(String key, Object obj) {
        return this.getRequestParameter().addObjectParameter(key, obj);
    }

    public void setStringValue(String stringValue) {
        this.stringValue = stringValue;
    }

    public String toString() {
        if (this.stringValue == null) {
            return super.toString();
        }
        return this.stringValue;
    }

}