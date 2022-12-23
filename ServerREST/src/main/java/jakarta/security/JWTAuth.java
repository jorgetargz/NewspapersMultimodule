package jakarta.security;

import jakarta.common.Constantes;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.security.enterprise.AuthenticationStatus;
import jakarta.security.enterprise.authentication.mechanism.http.HttpAuthenticationMechanism;
import jakarta.security.enterprise.authentication.mechanism.http.HttpMessageContext;
import jakarta.security.enterprise.credential.BasicAuthenticationCredential;
import jakarta.security.enterprise.identitystore.CredentialValidationResult;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.ws.rs.core.HttpHeaders;


@ApplicationScoped
public class JWTAuth implements HttpAuthenticationMechanism {

    @Inject
    private InMemoryIdentityStore identity;


    @Override
    public AuthenticationStatus validateRequest(HttpServletRequest httpServletRequest,
                                                HttpServletResponse httpServletResponse
            , HttpMessageContext httpMessageContext) {

        CredentialValidationResult c = null;

        String header = httpServletRequest.getHeader(HttpHeaders.AUTHORIZATION);
        if (header != null) {
            String[] valores = header.split(Constantes.WHITE_SPACE);

            c = basicAuthentication(httpServletRequest, valores);

            //JWT Authentication


        } else {
            if (httpServletRequest.getSession().getAttribute(Constantes.CREDENTIAL) != null)
                c = (CredentialValidationResult) httpServletRequest.getSession().getAttribute(Constantes.CREDENTIAL);
        }

        if (c != null) {

            if (c.getStatus() == CredentialValidationResult.Status.VALID) {
                return httpMessageContext.notifyContainerAboutLogin(c);
            }

            if (c.getStatus() == CredentialValidationResult.Status.INVALID) {
                httpServletRequest.setAttribute(Constantes.ERROR_LOGIN, Constantes.INVALID_CREDENTIALS);
            } else if (c.getStatus() == CredentialValidationResult.Status.NOT_VALIDATED) {
                httpServletRequest.setAttribute(Constantes.ERROR_LOGIN, Constantes.EMAIL_IS_NOT_VERIFIED);
            }
            return httpMessageContext.doNothing();

        } else {
            httpServletRequest.setAttribute(Constantes.ERROR_LOGIN, Constantes.LOGIN_REQUIRED);
            return httpMessageContext.doNothing();
        }
    }

    private CredentialValidationResult basicAuthentication(HttpServletRequest httpServletRequest, String[] valores) {
        CredentialValidationResult c = null;
        if (valores[0].equalsIgnoreCase(Constantes.BASIC)) {
            c = identity.validate(new BasicAuthenticationCredential(valores[1]));
            if (c.getStatus() == CredentialValidationResult.Status.VALID) {
                httpServletRequest.getSession().setAttribute(Constantes.CREDENTIAL, c);
            }
        }
        return c;
    }
}
