package com.dhenton9000.spring.mvc.controllers;

import java.io.Serializable;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;

public class TokenRequest implements Serializable {

    private static long serialVersionUID = -3687573109684994526L;

    private String state = "alpha";
    private String authorizeURI = null;
    private String clientId = null;
    private String scope = "openid";
    private UUID nonce = UUID.randomUUID();
    private String responseType;
    private String reponseMode;

    /**
     * @return the serialVersionUID
     */
    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    /**
     * @param aSerialVersionUID the serialVersionUID to set
     */
    public static void setSerialVersionUID(long aSerialVersionUID) {
        serialVersionUID = aSerialVersionUID;
    }

    

    /**
     * @return the state
     */
    public String getState() {
        return state;
    }

    /**
     * @param state the state to set
     */
    public void setState(String state) {
        this.state = state;
    }

    /**
     * @return the authorizeURI
     */
    public String getAuthorizeURI() {
        return authorizeURI;
    }

    /**
     * @param authorizeURI the authorizeURI to set
     */
    public void setAuthorizeURI(String authorizeURI) {
        this.authorizeURI = authorizeURI;
    }

    /**
     * @return the clientId
     */
    public String getClientId() {
        return clientId;
    }

    /**
     * @param clientId the clientId to set
     */
    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    /**
     * @return the scope
     */
    public String getScope() {
        return scope;
    }

    /**
     * @param scope the scope to set
     */
    public void setScope(String scope) {
        this.scope = scope;
    }

    /**
     * @return the nonce
     */
    public UUID getNonce() {
        return nonce;
    }

    /**
     * @param nonce the nonce to set
     */
    public void setNonce(UUID nonce) {
        this.nonce = nonce;
    }

    /**
     * @return the responseType
     */
    public String getResponseType() {
        return responseType;
    }

    /**
     * @param responseType the responseType to set
     */
    public void setResponseType(String responseType) {
        this.responseType = responseType;
    }

    /**
     * @return the reponseMode
     */
    public String getReponseMode() {
        return reponseMode;
    }

    /**
     * @param reponseMode the reponseMode to set
     */
    public void setReponseMode(String reponseMode) {
        this.reponseMode = reponseMode;
    }

}
