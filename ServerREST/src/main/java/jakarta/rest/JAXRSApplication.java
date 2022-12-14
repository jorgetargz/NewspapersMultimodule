package jakarta.rest;


import common.ConstantesAPI;
import jakarta.annotation.security.DeclareRoles;
import jakarta.ws.rs.ApplicationPath;
import jakarta.ws.rs.core.Application;

@ApplicationPath(ConstantesAPI.API_PATH)
@DeclareRoles({ConstantesAPI.ROLE_ADMIN, ConstantesAPI.ROLE_READER})
public class JAXRSApplication extends Application {
}
