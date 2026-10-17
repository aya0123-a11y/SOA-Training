package entities;

import java.util.Objects;

public class Module {
    private String matricule;
    private String nom;
    private int coefficient;
    private int volumeHoraire;
    private TypeModule type;
    private UniteEnseignement uniteEnseignement; // Référence vers l'UE associée
    public Module(){}
    // Constructeur
    public Module(String matricule, String nom, int coefficient, int volumeHoraire, TypeModule type) {
        this.matricule = matricule;
        this.nom = nom;
        this.coefficient = coefficient;
        this.volumeHoraire = volumeHoraire;
        this.type = type;
    }
    public Module(String matricule, String nom, int coefficient, int volumeHoraire, TypeModule type,UniteEnseignement uniteEnseignement) {
        this.matricule = matricule;
        this.nom = nom;
        this.coefficient = coefficient;
        this.volumeHoraire = volumeHoraire;
        this.type = type;
        this.uniteEnseignement=uniteEnseignement;
    }
    // Enumération TypeModule
    public enum TypeModule {
        TRANSVERSAL,
        PROFESSIONNEL,
        RECHERCHE
    }

    // Getters et Setters
    public String getMatricule() {
        return matricule;
    }

    public void setMatricule(String matricule) {
        this.matricule = matricule;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public int getCoefficient() {
        return coefficient;
    }

    public void setCoefficient(int coefficient) {
        this.coefficient = coefficient;
    }

    public int getVolumeHoraire() {
        return volumeHoraire;
    }

    public void setVolumeHoraire(int volumeHoraire) {
        this.volumeHoraire = volumeHoraire;
    }

    public TypeModule getType() {
        return type;
    }

    public void setType(TypeModule type) {
        this.type = type;
    }

    public UniteEnseignement getUniteEnseignement() {
        return uniteEnseignement;
    }

    public void setUniteEnseignement(UniteEnseignement uniteEnseignement) {
        this.uniteEnseignement = uniteEnseignement;
    }

    // equals() : utilisée par les collections pour savoir si elles contiennent un module donné
    // (deux modules sont égaux s'ils ont la même matricule)
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Module module = (Module) o;
        return Objects.equals(matricule, module.matricule);
    }

    // hashCode() : utilisée pour savoir où stocker / chercher l'objet en interne (HashSet, HashMap...)
    @Override
    public int hashCode() {
        return Objects.hash(matricule);
    }

    @Override
    public String toString() {
        return "Module{matricule='" + matricule + "', nom='" + nom + "', coefficient=" + coefficient
                + ", volumeHoraire=" + volumeHoraire + ", type=" + type + "}";
    }

}