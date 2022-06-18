package net.atos.sapps_backend.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class Service {

    String id;
    Integer code;
    String libelle;
    String type;
    String socle;
    String description;
    String dateMep;
    String statut;
    String responsableSocle;
 String objetMetier ;
    String dateDebut;
    String dateFin;
    String respoSocle;


    public String getRespoSocle() {
        return respoSocle;
    }

    public void setRespoSocle(String respoSocle) {
        this.respoSocle = respoSocle;
    }

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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSocle() {
        return socle;
    }

    public void setSocle(String socle) {
        this.socle = socle;
    }



    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDateMep() {
        return dateMep;
    }

    public void setDateMep(String dateMep) {
        this.dateMep = dateMep;
    }

    public String getStatut() {
        return statut;
    }

    public void setStatut(String statut) {
        this.statut = statut;
    }

    public String getResponsableSocle() {
        return responsableSocle;
    }

    public void setResponsableSocle(String responsableSocle) {
        this.responsableSocle = responsableSocle;
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


    @SuppressWarnings("unchecked")
    @JsonProperty("content")
    private void unpackNested(Map<String,Object> content) {
        Map<String, String> s_code = (Map<String, String>) content.get("s_CODE");
        this.id = (String) s_code.get("content");

        Map<String, Integer> i_code = (Map<String, Integer>) content.get("i_CODE");
        this.code = (Integer) i_code.get("content");

        Map<String, String> s_libelle = (Map<String, String>) content.get("s_LIBELLE");
        this.libelle = (String) s_libelle.get("content");

        Map<String, String> s_type_serv = (Map<String, String>) content.get("s_TYPE_SERV");
        if (s_type_serv.get("label") == null)
            this.type= "Aucun";
        else
            this.type = (String) s_type_serv.get("label");

        Map<String, String> s_socle = (Map<String, String>) content.get("s_SOCLE");
        if (s_socle.get("label") == null)
            this.socle= "Aucun";
        else
            this.socle = (String) s_socle.get("label");

        Map<String, String> s_description = (Map<String, String>) content.get("s_DESCRIPTION");
        this.description = (String) s_description.get("content");


        Map<String, String> s_responsable_socle = (Map<String, String>) content.get("s_RESP_SOCLE");
        if (s_responsable_socle.get("content") == null)
            this.respoSocle= "Aucun";
        else
            this.respoSocle = (String) s_responsable_socle.get("content");


//        Map<String,ArrayList<String> > s_OBJET_METIER = (Map<String,ArrayList<String> >) content.get("s_OBJET_METIER");
////        this.objetMetier = (String) (s_OBJET_METIER.get("content"));




        Map<String, String> d_date_debut = (Map<String, String>) content.get("d_DATE_DEBUT");
        this.dateDebut = (String) d_date_debut.get("content");

        Map<String, String> d_date_fin = (Map<String, String>) content.get("d_DATE_FIN");
        this.dateFin = (String) d_date_fin.get("content");


    }



}
