package rest.utilities;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

/**
 * Active les ressources REST de l'application.
 * Le chemin "rest" est ajouté à l'URL de base :
 * http://localhost:8080/Gestion_UE/rest/...
 */
@ApplicationPath("rest")
public class RestActivator extends Application {
}
