package net.atos.sapps_backend.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Map;

public class TopicFonctionnel {

    String id;
    Integer code;
    String libelle;
    String socle;
    String categorie;
    String objetMetier;
    String dateDebut;
    String dateFin;
    String description;

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

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public String getSocle() {
        return socle;
    }

    public void setSocle(String socle) {
        this.socle = socle;
    }

    public String getCategorie() {
        return categorie;
    }

    public void setCategorie(String categorie) {
        this.categorie = categorie;
    }

    public String getObjetMetier() {
        return objetMetier;
    }

    public void setObjetMetier(String objetMetier) {
        this.objetMetier = objetMetier;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }



    @SuppressWarnings("unchecked")
    @JsonProperty("content")
    private void unpackNested(Map<String,Object> content) {
        Map<String, String> s_code = (Map<String, String>) content.get("s_CODE");
        this.id = (String) s_code.get("content");

        Map<String, Integer> i_code = (Map<String, Integer>) content.get("i_CODE");
        this.code = (Integer) i_code.get("content");

        Map<String, String> s_libelle = (Map<String, String>) content.get("s_LIBELLE");
        this.libelle = (String) s_libelle.get("content");

        Map<String, String> s_socle = (Map<String, String>) content.get("s_SOCLE");
        this.socle = (String) s_socle.get("label");

        Map<String, String> s_catego_topic = (Map<String, String>) content.get("s_CATEGO_TOPIC");
        this.categorie = (String) s_catego_topic.get("label");

        Map<String, String> s_objet_metier = (Map<String, String>) content.get("s_OBJET_METIER");
        this.objetMetier = (String) s_objet_metier.get("label");

        Map<String, String> s_description = (Map<String, String>) content.get("s_DESCRIPTION");
        this.description = (String) s_description.get("content");

        Map<String, String> d_date_debut = (Map<String, String>) content.get("d_DATE_DEBUT");
        this.dateDebut = (String) d_date_debut.get("content");

        Map<String, String> d_date_fin = (Map<String, String>) content.get("d_DATE_FIN");
        this.dateFin = (String) d_date_fin.get("content");



    }
}
