package com.merchant.exception;

import java.util.Map;

// Requête incohérente ou donnée invalide -> HTTP 400
public class BadRequestException extends RuntimeException {

    // détail champ par champ (peut être null)
    private final Map<String, String> erreurs;

    public BadRequestException(String message) {
        this(message, null);
    }

    public BadRequestException(String message, Map<String, String> erreurs) {
        super(message);
        this.erreurs = erreurs;
    }

    public Map<String, String> getErreurs() {
        return erreurs;
    }
}
