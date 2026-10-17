package rest.resources;

import entities.Module;
import entities.UniteEnseignement;
import metiers.ModuleBusiness;
import metiers.UniteEnseignementBusiness;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

/**
 * Ressource REST : Module
 * URL de base : http://localhost:8080/Gestion_UE/rest/modules
 */
@Path("modules")
public class ModuleResource {

    private static ModuleBusiness helper = new ModuleBusiness();
    private static UniteEnseignementBusiness ueHelper = new UniteEnseignementBusiness();

    // 1) Création d'un nouveau module (l'UE indiquée doit exister)
    // POST /rest/modules
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    public Response addModule(Module module) {
        if (module == null || module.getUniteEnseignement() == null) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("Le module doit contenir une uniteEnseignement (avec son code)").build();
        }
        if (helper.getModuleByMatricule(module.getMatricule()) != null) {
            return Response.status(Response.Status.CONFLICT)
                    .entity("Un module avec la matricule " + module.getMatricule() + " existe déjà").build();
        }
        if (helper.addModule(module)) {
            return Response.status(Response.Status.CREATED).entity("Module ajouté avec succès").build();
        }
        return Response.status(Response.Status.NOT_FOUND)
                .entity("UE introuvable (code " + module.getUniteEnseignement().getCode() + ")").build();
    }

    // 2) Récupération de la liste de tous les modules
    // GET /rest/modules
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllModules() {
        List<Module> liste = helper.getAllModules();
        if (liste.isEmpty()) {
            return Response.status(Response.Status.NO_CONTENT).build();
        }
        return Response.ok(liste).build();
    }

    // 3) Récupération d'un module ayant un identifiant (matricule) spécifique
    // GET /rest/modules/{matricule}
    @GET
    @Path("{matricule}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getModuleByMatricule(@PathParam("matricule") String matricule) {
        Module module = helper.getModuleByMatricule(matricule);
        if (module == null) {
            return Response.status(Response.Status.NOT_FOUND)
                    .type(MediaType.TEXT_PLAIN)
                    .entity("Module introuvable (matricule " + matricule + ")").build();
        }
        return Response.ok(module).build();
    }

    // 4) Suppression d'un module ayant une matricule spécifique
    // DELETE /rest/modules/{matricule}
    @DELETE
    @Path("{matricule}")
    @Produces(MediaType.TEXT_PLAIN)
    public Response deleteModule(@PathParam("matricule") String matricule) {
        if (helper.deleteModule(matricule)) {
            return Response.ok("Module supprimé avec succès").build();
        }
        return Response.status(Response.Status.NOT_FOUND)
                .entity("Module introuvable (matricule " + matricule + ")").build();
    }

    // 5) Modification d'un module ayant une matricule spécifique
    // PUT /rest/modules/{matricule}
    @PUT
    @Path("{matricule}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    public Response updateModule(@PathParam("matricule") String matricule, Module module) {
        if (module == null) {
            return Response.status(Response.Status.BAD_REQUEST).entity("Corps de la requête vide").build();
        }
        Module existant = helper.getModuleByMatricule(matricule);
        if (existant == null) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("Module introuvable (matricule " + matricule + ")").build();
        }
        module.setMatricule(matricule); // la matricule de l'URL fait foi

        // UE : si elle est fournie, elle doit exister ; sinon on garde l'UE actuelle du module
        if (module.getUniteEnseignement() != null) {
            UniteEnseignement ue = ueHelper.getUEByCode(module.getUniteEnseignement().getCode());
            if (ue == null) {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity("UE introuvable (code " + module.getUniteEnseignement().getCode() + ")").build();
            }
            module.setUniteEnseignement(ue);
        } else {
            module.setUniteEnseignement(existant.getUniteEnseignement());
        }

        helper.updateModule(matricule, module);
        return Response.ok("Module modifié avec succès").build();
    }

    // 6) Récupérer tous les modules associés à une UE spécifique, identifiée par son code (codeUE)
    // GET /rest/modules/ue/{codeUE}
    @GET
    @Path("ue/{codeUE}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getModulesByUE(@PathParam("codeUE") int codeUE) {
        UniteEnseignement ue = ueHelper.getUEByCode(codeUE);
        if (ue == null) {
            return Response.status(Response.Status.NOT_FOUND)
                    .type(MediaType.TEXT_PLAIN)
                    .entity("UE introuvable (code " + codeUE + ")").build();
        }
        List<Module> liste = helper.getModulesByUE(ue);
        if (liste.isEmpty()) {
            return Response.status(Response.Status.NOT_FOUND)
                    .type(MediaType.TEXT_PLAIN)
                    .entity("Aucun module pour l'UE " + codeUE).build();
        }
        return Response.ok(liste).build();
    }
}
