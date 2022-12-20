package jakarta.security;

import domain.services.ServicesLogin;
import jakarta.inject.Inject;
import jakarta.security.enterprise.credential.BasicAuthenticationCredential;
import jakarta.security.enterprise.credential.Credential;
import jakarta.security.enterprise.identitystore.CredentialValidationResult;
import jakarta.security.enterprise.identitystore.IdentityStore;
import lombok.extern.log4j.Log4j2;
import modelo.Reader;

import java.util.Set;

import static jakarta.security.enterprise.identitystore.CredentialValidationResult.INVALID_RESULT;

@Log4j2
public class InMemoryIdentityStore implements IdentityStore {

    private final ServicesLogin serviciosLogin;

    @Override
    public int priority() {
        return 10;
    }

    @Inject
    public InMemoryIdentityStore(ServicesLogin serviciosLogin) {
        this.serviciosLogin = serviciosLogin;
    }

    @Override
    public CredentialValidationResult validate(Credential credential) {

        if (credential instanceof BasicAuthenticationCredential basicAuthenticationCredential) {
            Reader loguedUser;
            try {
                loguedUser = serviciosLogin.login(basicAuthenticationCredential.getCaller(), basicAuthenticationCredential.getPassword().getValue());
                return new CredentialValidationResult(loguedUser.getLogin().getUsername(), Set.of(loguedUser.getLogin().getRole()));
            } catch (Exception e) {
                log.error(e.getMessage(), e);
                return INVALID_RESULT;
            }
        } else {
            return INVALID_RESULT;
        }
    }
}
