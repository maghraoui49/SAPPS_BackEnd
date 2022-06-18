package net.atos.sapps_backend.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Map;

public class Socle {

    private String id;
    private Integer code;
    private String libelleNorme;
    private String domaine;
    //private String domaineName;
    private String resp;
    private String archi;
    //private String end;
    private String dateFin; // date de fin ou de décommissionnement
    private String libelle;
    private String libellePrefixe;
    private String categorie;
    private String description;

    private String dateDebut;

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

    public String getLibelleNorme() {
        return libelleNorme;
    }

    public void setLibelleNorme(String libelleNorme) {
        this.libelleNorme = libelleNorme;
    }

    public String getDomaine() {
        return domaine;
    }

    public void setDomaine(String domaine) {
        this.domaine = domaine;
    }

//    public String getDomaineName() {
//        return domaineName;
//    }
//
//    public void setDomaineName(String domaineName) {
//        this.domaineName = domaineName;
//    }

    public String getResp() {
        return resp;
    }

    public void setResp(String resp) {
        this.resp = resp;
    }

    public String getArchi() {
        return archi;
    }

    public void setArchi(String archi) {
        this.archi = archi;
    }

//    public String getEnd() {
//        return end;
//    }
//
//    public void setEnd(String end) {
//        this.end = end;
//    }

    public String getDateFin() {
        return dateFin;
    }

    public void setDateFin(String dateFin) {
        this.dateFin = dateFin;
    }

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public String getLibellePrefixe() {
        return libellePrefixe;
    }

    public void setLibellePrefixe(String libellePrefixe) {
        this.libellePrefixe = libellePrefixe;
    }

    public String getCategorie() {
        return categorie;
    }

    public void setCategorie(String categorie) {
        this.categorie = categorie;
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


    @SuppressWarnings("unchecked")
    @JsonProperty("content")
    private void unpackNested(Map<String,Object> content) {
        Map<String, String> s_code = (Map<String, String>) content.get("s_CODE");
        this.id = (String) s_code.get("content");

        Map<String, Integer> i_code = (Map<String, Integer>) content.get("i_CODE");
        this.code = (Integer) i_code.get("content");

        Map<String, String> s_libelle_norme = (Map<String, String>) content.get("s_LIBELLE_NORME");
        this.libelleNorme = (String) s_libelle_norme.get("content");

        Map<String, String> s_domaine = (Map<String, String>) content.get("s_DOMAINE");
        this.domaine = (String) s_domaine.get("label");

        Map<String, String> s_responsableSOCLE = (Map<String, String>) content.get("s_ResponsableSOCLE");
        this.resp = (String) s_responsableSOCLE.get("label");

        Map<String, String> s_archiSOCLE = (Map<String, String>) content.get("s_ArchiSOCLE");
        if (s_archiSOCLE.get("label") == null)
            this.archi= "Aucun";
        else
            this.archi = (String) s_archiSOCLE.get("label");

        Map<String, String> d_date_fin = (Map<String, String>) content.get("d_DATE_FIN");
        this.dateFin = (String) d_date_fin.get("content");

        Map<String, String> s_libelle = (Map<String, String>) content.get("s_LIBELLE");
        this.libelle = (String) s_libelle.get("content");

        Map<String, String> contentMap2 = (Map<String, String>) content.get("s_LIBELLE_PREFIXE");
        this.libellePrefixe = (String) contentMap2.get("content");

        Map<String, String> s_categorie = (Map<String, String>) content.get("s_CATEGORIE");
        if (s_categorie.get("content") == null)
            this.categorie= "Aucun";
        else
            this.categorie = (String) s_categorie.get("content");

        Map<String, String> s_description = (Map<String, String>) content.get("s_DESCRIPTION");
        this.description = (String) s_description.get("content");

        Map<String, String> d_date_debut = (Map<String, String>) content.get("d_DATE_DEBUT");
        this.dateDebut = (String) d_date_debut.get("content");



    }




}
