package rest.resources;

import entities.UniteEnseignement;
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
 * Ressource REST : Unité d'enseignement
 * URL de base : http://localhost:8080/Gestion_UE/rest/UE
 */
@Path("UE")
public class UniteEnseignementResource {

    private static UniteEnseignementBusiness helper = new UniteEnseignementBusiness();

    // 1) Création d'une nouvelle unité d'enseignement
    // POST /rest/UE
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    public Response addUE(UniteEnseignement ue) {
        if (ue == null) {
            return Response.status(Response.Status.BAD_REQUEST).entity("Corps de la requête vide").build();
        }
        if (helper.getUEByCode(ue.getCode()) != null) {
            return Response.status(Response.Status.CONFLICT)
                    .entity("Une UE avec le code " + ue.getCode() + " existe déjà").build();
        }
        if (helper.addUniteEnseignement(ue)) {
            return Response.status(Response.Status.CREATED).entity("UE ajoutée avec succès").build();
        }
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Echec de l'ajout").build();
    }

    // 2) Récupération de la liste de toutes les unités d'enseignement
    // GET /rest/UE
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllUE() {
        List<UniteEnseignement> liste = helper.getListeUE();
        if (liste.isEmpty()) {
            return Response.status(Response.Status.NO_CONTENT).build();
        }
        return Response.ok(liste).build();
    }

    // 3) Récupération de la liste des unités d'enseignement d'un semestre spécifique
    // GET /rest/UE/semestre/{semestre}
    @GET
    @Path("semestre/{semestre}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUEBySemestre(@PathParam("semestre") int semestre) {
        List<UniteEnseignement> liste = helper.getUEBySemestre(semestre);
        if (liste.isEmpty()) {
            return Response.status(Response.Status.NOT_FOUND)
                    .type(MediaType.TEXT_PLAIN)
                    .entity("Aucune UE pour le semestre " + semestre).build();
        }
        return Response.ok(liste).build();
    }

    // 4) Suppression d'une unité d'enseignement ayant un identifiant (code) spécifique
    // DELETE /rest/UE/{code}
    @DELETE
    @Path("{code}")
    @Produces(MediaType.TEXT_PLAIN)
    public Response deleteUE(@PathParam("code") int code) {
        if (helper.deleteUniteEnseignement(code)) {
            return Response.ok("UE supprimée avec succès").build();
        }
        return Response.status(Response.Status.NOT_FOUND).entity("UE introuvable (code " + code + ")").build();
    }

    // 5) Modification d'une unité d'enseignement ayant un identifiant (code) spécifique
    // PUT /rest/UE/{code}
    @PUT
    @Path("{code}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    public Response updateUE(@PathParam("code") int code, UniteEnseignement ue) {
        if (ue == null) {
            return Response.status(Response.Status.BAD_REQUEST).entity("Corps de la requête vide").build();
        }
        ue.setCode(code); // le code de l'URL fait foi
        if (helper.updateUniteEnseignement(code, ue)) {
            return Response.ok("UE modifiée avec succès").build();
        }
        return Response.status(Response.Status.NOT_FOUND).entity("UE introuvable (code " + code + ")").build();
    }

    // 6) Récupération d'une unité d'enseignement ayant un code donné
    // GET /rest/UE/{code}
    @GET
    @Path("{code}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUEByCode(@PathParam("code") int code) {
        UniteEnseignement ue = helper.getUEByCode(code);
        if (ue == null) {
            return Response.status(Response.Status.NOT_FOUND)
                    .type(MediaType.TEXT_PLAIN)
                    .entity("UE introuvable (code " + code + ")").build();
        }
        return Response.ok(ue).build();
    }
}
