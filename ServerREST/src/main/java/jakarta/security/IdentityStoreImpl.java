package jakarta.security;

import dao.excepciones.DatabaseException;
import dao.excepciones.NotFoundException;
import domain.common.Constantes;
import domain.services.ServicesLogin;
import domain.services.excepciones.ValidationException;
import jakarta.inject.Inject;
import jakarta.security.enterprise.credential.BasicAuthenticationCredential;
import jakarta.security.enterprise.credential.Credential;
import jakarta.security.enterprise.identitystore.CredentialValidationResult;
import jakarta.security.enterprise.identitystore.IdentityStore;
import lombok.extern.log4j.Log4j2;
import modelo.Reader;

import java.util.Set;

import static jakarta.security.enterprise.identitystore.CredentialValidationResult.INVALID_RESULT;
import static jakarta.security.enterprise.identitystore.CredentialValidationResult.NOT_VALIDATED_RESULT;

@Log4j2
public class IdentityStoreImpl implements IdentityStore {

    private final ServicesLogin serviciosLogin;

    @Override
    public int priority() {
        return 10;
    }

    @Inject
    public IdentityStoreImpl(ServicesLogin serviciosLogin) {
        this.serviciosLogin = serviciosLogin;
    }

    @Override
    public CredentialValidationResult validate(Credential credential) {

        if (credential instanceof BasicAuthenticationCredential basicAuthenticationCredential) {
            Reader loggedUser;
            try {
                loggedUser = serviciosLogin.login(basicAuthenticationCredential.getCaller(), basicAuthenticationCredential.getPassword().getValue());
                return new CredentialValidationResult(loggedUser.getLogin().getUsername(), Set.of(loggedUser.getLogin().getRole()));
            } catch (DatabaseException | NotFoundException e) {
                return INVALID_RESULT;
            } catch (ValidationException e) {
                if (e.getMessage().equals(Constantes.EMAIL_IS_NOT_VERIFIED)) {
                    return NOT_VALIDATED_RESULT;
                } else {
                    return INVALID_RESULT;
                }
            }
        } else {
            return null;
        }
    }
}
