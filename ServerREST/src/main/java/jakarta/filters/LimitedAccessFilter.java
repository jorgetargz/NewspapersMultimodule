package jakarta.filters;


import common.ConstantesAPI;
import configuration.Configuration;
import domain.services.ServicesPetitions;
import jakarta.common.Constantes;
import jakarta.inject.Inject;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerRequestFilter;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.SecurityContext;
import jakarta.ws.rs.ext.Provider;
import lombok.extern.log4j.Log4j2;
import modelo.BaseError;
import modelo.Petition;

import java.time.LocalDateTime;

@Provider
@LimitedAccess
@Log4j2
public class LimitedAccessFilter implements ContainerRequestFilter {

    @Context
    private HttpServletRequest request;

    private final ServicesPetitions servicesPetitions;
    private final Configuration configuration;

    @Inject
    public LimitedAccessFilter(ServicesPetitions servicesPetitions, Configuration configuration) {
        this.servicesPetitions = servicesPetitions;
        this.configuration = configuration;
    }

    @Override
    public void filter(ContainerRequestContext containerRequestContext) {

        SecurityContext securityContext = containerRequestContext.getSecurityContext();

        if (securityContext.isUserInRole(ConstantesAPI.ROLE_READER)) {
            String username = securityContext.getUserPrincipal().getName();
            if (servicesPetitions.getPetitionsInCurrentPeriodByUsername(username).size() >= configuration.getMaxPetitionsPerPeriod()) {
                containerRequestContext.abortWith(Response.status(Response.Status.FORBIDDEN)
                        .entity(new BaseError(Constantes.SUPERADO_EL_MAXIMO_DE_PETICIONES, LocalDateTime.now()))
                        .type(MediaType.APPLICATION_JSON_TYPE).build());
            } else {
                Petition petition = new Petition();
                petition.setUsername(username);
                petition.setPath(request.getRequestURI());
                petition.setMethod(request.getMethod());
                petition.setTime(LocalDateTime.now());
                petition = servicesPetitions.savePetition(petition);
                log.info(petition);
            }
        }
    }
}