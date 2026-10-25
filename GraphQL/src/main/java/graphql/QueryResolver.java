package graphql;

import business.ModuleBusiness;
import business.UniteEnseignementBusiness;
import com.coxautodev.graphql.tools.GraphQLRootResolver;
import entities.Module;
import entities.UniteEnseignement;

import java.util.List;

public class QueryResolver implements GraphQLRootResolver {

    public UniteEnseignementBusiness helper;
    public ModuleBusiness moduleHelper;

    public QueryResolver() {
        helper = new UniteEnseignementBusiness();
        moduleHelper = new ModuleBusiness();
    }

    // ===================== Unités d'enseignement =====================

    // query { allUEs { code domaine } }
    public List<UniteEnseignement> allUEs() {
        return helper.getListeUE();
    }

    // query { getUEByCode(code: 1) { code domaine responsable } }
    public UniteEnseignement getUEByCode(int code) {
        return helper.getUEByCode(code);
    }

    // query { getUEsByDomaine(domaine: "Informatique") { code responsable } }
    public List<UniteEnseignement> getUEsByDomaine(String domaine) {
        return helper.getUEByDomaine(domaine);
    }

    // query { getUEsBySemestre(semestre: 1) { code domaine } }
    public List<UniteEnseignement> getUEsBySemestre(int semestre) {
        return helper.getUEBySemestre(semestre);
    }

    // ============================ Modules ============================

    // query { getAllModules { matricule nom coefficient volumeHoraire } }
    public List<Module> getAllModules() {
        return moduleHelper.getAllModules();
    }

    // query { getModuleByMatricule(matricule: "M101") { matricule nom } }
    public Module getModuleByMatricule(String matricule) {
        return moduleHelper.getModuleByMatricule(matricule);
    }
}