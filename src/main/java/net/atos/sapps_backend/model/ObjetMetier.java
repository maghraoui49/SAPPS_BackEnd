package net.atos.sapps_backend.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Map;

public class ObjetMetier {


    String id;
    Integer code;
    String nom;
    String name;
    String socle;
    String responsableSocle;
    String description;
    String dateDebut;
    String dateFin;
    String domaine;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSocle() {
        return socle;
    }

    public void setSocle(String socle) {
        this.socle = socle;
    }

    public String getResponsableSocle() {
        return responsableSocle;
    }

    public void setResponsableSocle(String responsableSocle) {
        this.responsableSocle = responsableSocle;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDateDebut() {
        return dateDebut;
    }

    public void setDateDebut(String dateDebut) {
        this.dateDebut = dateDebut;
    }

    public String getDateFin() {
        return dateFin;
    }

    public void setDateFin(String dateFin) {
        this.dateFin = dateFin;
    }

    public String getDomaine() {
        return domaine;
    }

    public void setDomaine(String domaine) {
        this.domaine = domaine;
    }




    @SuppressWarnings("unchecked")
    @JsonProperty("content")
    private void unpackNested(Map<String,Object> content) {
        Map<String, String> s_code = (Map<String, String>) content.get("s_CODE");
        this.id = (String) s_code.get("content");

        Map<String, Integer> i_code = (Map<String, Integer>) content.get("i_CODE");
        this.code = (Integer) i_code.get("content");

        Map<String, String> s_libelle_fr = (Map<String, String>) content.get("s_LIBELLE_FR");
        this.nom = (String) s_libelle_fr.get("content");

        Map<String, String> s_libelle_en = (Map<String, String>) content.get("s_LIBELLE_EN");
        this.name = (String) s_libelle_en.get("content");

        Map<String, String> s_socle = (Map<String, String>) content.get("s_SOCLE");
        if (s_socle.get("label") == null)
            this.socle= "Aucun";
        else
            this.socle = (String) s_socle.get("label");

        Map<String, String> s_resp_socle = (Map<String, String>) content.get("s_RESP_SOCLE");
        if (s_resp_socle.get("content") == null)
            this.responsableSocle= "Aucun";
        else
            this.responsableSocle = (String) s_resp_socle.get("content");

        Map<String, String> s_description = (Map<String, String>) content.get("s_DESCRIPTION");
        this.description = (String) s_description.get("content");

        Map<String, String> d_date_debut = (Map<String, String>) content.get("d_DATE_DEBUT");
        this.dateDebut = (String) d_date_debut.get("content");

        Map<String, String> d_date_fin = (Map<String, String>) content.get("d_DATE_FIN");
        this.dateFin = (String) d_date_fin.get("content");

        Map<String, String> s_domaine = (Map<String, String>) content.get("s_DOMAINE");
        this.domaine = (String) s_domaine.get("content");



    }
}
