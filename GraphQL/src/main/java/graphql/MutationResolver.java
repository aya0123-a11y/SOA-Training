package graphql;

import business.ModuleBusiness;
import business.UniteEnseignementBusiness;
import com.coxautodev.graphql.tools.GraphQLRootResolver;
import entities.Module;
import entities.UniteEnseignement;

public class MutationResolver implements GraphQLRootResolver {

    public UniteEnseignementBusiness helper;
    public ModuleBusiness moduleHelper;

    public MutationResolver() {
        helper = new UniteEnseignementBusiness();
        moduleHelper = new ModuleBusiness();
    }

    // ===================== Unités d'enseignement =====================

    // mutation { addUniteEnseignement(code: 1, domaine: "Informatique", responsable: "Ben Ali", credits: 6, semestre: 1) }
    public boolean addUniteEnseignement(int code, String domaine, String responsable, int credits, int semestre) {
        UniteEnseignement ue = new UniteEnseignement(code, domaine, responsable, credits, semestre);
        return helper.addUniteEnseignement(ue);
    }

    // mutation { updateUniteEnseignement(code: 1, responsable: "Trabelsi", credits: 5) }
    public boolean updateUniteEnseignement(int code, String domaine, String responsable, Integer credits, Integer semestre) {
        UniteEnseignement ue = helper.getUEByCode(code);
        if (ue == null) {
            return false;
        }

        if (domaine != null)     ue.setDomaine(domaine);
        if (responsable != null) ue.setResponsable(responsable);
        if (credits != null)     ue.setCredits(credits);
        if (semestre != null)    ue.setSemestre(semestre);

        return helper.updateUniteEnseignement(code, ue);
    }

    // mutation { deleteUniteEnseignement(code: 1) }
    public boolean deleteUniteEnseignement(int code) {
        return helper.deleteUniteEnseignement(code);
    }

    // ============================ Modules ============================

    // mutation { addModule(matricule: "M101", libelle: "SOA", coef: 2, volumeHoraire: 21, codeUE: 1) }
    public boolean addModule(String matricule, String libelle, int coef, int volumeHoraire, int codeUE) {
        UniteEnseignement ue = helper.getUEByCode(codeUE);
        if (ue == null) {
            return false; // l'UE doit exister avant d'y ajouter un module
        }
        Module module = new Module(matricule, libelle, coef, volumeHoraire);
        module.setUniteEnseignement(ue);
        return moduleHelper.addModule(module);
    }

    // mutation { updateModule(matricule: "M101", libelle: "Algorithmique", coef: 3) { matricule nom coefficient } }
    public Module updateModule(String matricule, String libelle, Integer coef, Integer volumeHoraire, Integer codeUE) {
        Module module = moduleHelper.getModuleByMatricule(matricule);
        if (module == null) {
            return null;
        }

        if (libelle != null)       module.setNom(libelle);
        if (coef != null)          module.setCoefficient(coef);
        if (volumeHoraire != null) module.setVolumeHoraire(volumeHoraire);
        if (codeUE != null) {
            UniteEnseignement ue = helper.getUEByCode(codeUE);
            if (ue != null) module.setUniteEnseignement(ue);
        }

        moduleHelper.updateModule(matricule, module);
        return module;
    }

    // mutation { deleteModule(matricule: "M101") }
    public String deleteModule(String matricule) {
        boolean ok = moduleHelper.deleteModule(matricule);
        return ok ? "deleted successfully" : "module not found";
    }
}