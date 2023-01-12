package jakarta.beans.utils;

import jakarta.common.Constantes;

import java.io.Serializable;
import java.security.SecureRandom;
import java.util.Base64;

public class VerificationCode implements Serializable {

    public String generate() {
        SecureRandom sr = new SecureRandom();
        byte[] bits = new byte[Constantes.VERIFICATION_CODE_LENGTH];
        sr.nextBytes(bits);
        return Base64.getUrlEncoder().encodeToString(bits);
    }
}
