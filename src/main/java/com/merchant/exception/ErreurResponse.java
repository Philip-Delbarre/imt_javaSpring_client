package com.merchant.exception;

import java.util.Map;

import com.fasterxml.jackson.annotation.JsonInclude;

/** Corps JSON renvoyé en cas d'erreur. */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ErreurResponse {

    private final int status;
    private final String message;
    // détail champ par champ (uniquement pour les erreurs de validation)
    private final Map<String, String> erreurs;

    public ErreurResponse(int status, String message) {
        this(status, message, null);
    }

    public ErreurResponse(int status, String message, Map<String, String> erreurs) {
        this.status = status;
        this.message = message;
        this.erreurs = erreurs;
    }

    public int getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public Map<String, String> getErreurs() {
        return erreurs;
    }
}
