package net.atos.sapps_backend.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
@JsonIgnoreProperties(ignoreUnknown = true)
public class Application {

        //s_CODE
        private String id;

        //s_LIBELLE_NORME
        private String nom;

        //s_GESTION_PATRIMOINE
        private String gestionPatrimoine;

        //s_SOUS_DOMAINE
        private String socle;

        //s_ETAT
        private String etat;

        private String exploitation;
        private String appartenance;
        private String deco;
        private String applicatif;
        private String responsable;
        private String domaine;
        private String categorie;

        Date dateDecommission;
        Date dateMiseEnService;
        String enTMA;


        public Date getDateDecommission() {
                return dateDecommission;
        }

        public void setDateDecommission(Date dateDecommission) {
                this.dateDecommission = dateDecommission;
        }

        public Date getDateMiseEnService() {
                return dateMiseEnService;
        }

        public void setDateMiseEnService(Date dateMiseEnService) {
                this.dateMiseEnService = dateMiseEnService;
        }

        public String getEnTMA() {
                return enTMA;
        }

        public void setEnTMA(String enTMA) {
                this.enTMA = enTMA;
        }

        public String getId() {
                return id;
        }

        public void setId(String id) {
                this.id = id;
        }

        public String getNom() {
                return nom;
        }

        public void setNom(String nom) {
                this.nom = nom;
        }

        public String getGestionPatrimoine() {
                return gestionPatrimoine;
        }

        public void setGestionPatrimoine(String gestionPatrimoine) {
                this.gestionPatrimoine = gestionPatrimoine;
        }

        public String getSocle() {
                return socle;
        }

        public void setSocle(String socle) {
                this.socle = socle;
        }

        public String getEtat() {
                return etat;
        }

        public void setEtat(String etat) {
                this.etat = etat;
        }

        public String getExploitation() {
                return exploitation;
        }

        public void setExploitation(String exploitation) {
                this.exploitation = exploitation;
        }

        public String getAppartenance() {
                return appartenance;
        }

        public void setAppartenance(String appartenance) {
                this.appartenance = appartenance;
        }

        public String getDeco() {
                return deco;
        }

        public void setDeco(String deco) {
                this.deco = deco;
        }

        public String getApplicatif() {
                return applicatif;
        }

        public void setApplicatif(String applicatif) {
                this.applicatif = applicatif;
        }

        public String getResponsable() {
                return responsable;
        }

        public void setResponsable(String responsable) {
                this.responsable = responsable;
        }

        public String getDomaine() {
                return domaine;
        }

        public void setDomaine(String domaine) {
                this.domaine = domaine;
        }

        public String getCategorie() {
                return categorie;
        }

        public void setCategorie(String categorie) {
                this.categorie = categorie;
        }




        @SuppressWarnings("unchecked")
        @JsonProperty("content")
        private void unpackNested(Map<String,Object> content) throws ParseException {
                DateFormat sourceFormat = new SimpleDateFormat("yyyy-mm-dd");

                Map<String,String> contentMap = (Map<String,String>)content.get("s_CODE");
                this.id = (String)contentMap.get("content");

                Map<String,String> s_libelle_norme = (Map<String,String>)content.get("s_LIBELLE_NORME");
                this.nom = (String)s_libelle_norme.get("content");

                Map<String,String> s_gestion_patrimoine = (Map<String,String>)content.get("s_GESTION_PATRIMOINE");
                if (s_gestion_patrimoine.get("label") == null)
                        this.gestionPatrimoine= "Aucun";
                else
                        this.gestionPatrimoine = (String) s_gestion_patrimoine.get("label");

                Map<String,String> sous_domaine = (Map<String,String>)content.get("s_SOUS_DOMAINE");
                this.socle = (String) sous_domaine.get("label");

                Map<String,String> s_etat = (Map<String,String>)content.get("s_ETAT");
                this.etat = (String) s_etat.get("label");

                Map<String,String> s_exploitation= (Map<String,String>)content.get("s_EXPLOITATION");
                if (s_exploitation.get("label") == null)
                        this.exploitation="Aucun";
                else
                        this.exploitation = (String) s_exploitation.get("label");

                Map<String,String> s_appartenance= (Map<String,String>)content.get("s_APPARTENANCE");
                this.appartenance = (String) s_appartenance.get("label");

                Map<String,String>  s_statut_deco= (Map<String,String>)content.get("s_STATUT_DECO");
                if (s_statut_deco.get("label") == null)
                        this.deco= "Aucun";
                else
                        this.deco = (String) s_statut_deco.get("label");

                Map<String,String> s_systeme_applicatif= (Map<String,String>)content.get("s_SYSTEME_APPLICATIF");
                if (s_systeme_applicatif.get("label")== null)
                        this.applicatif="Aucun";
                else
                this.applicatif = (String) s_systeme_applicatif.get("label");

                Map<String,String> s_departement_build_run= (Map<String,String>)content.get("s_DEPARTEMENT_BUILD_RUN");
                if (s_departement_build_run.get("label") == null)
                        this.responsable= "Aucun";
                else
                this.responsable = (String) s_departement_build_run.get("label");

                Map<String,String> s_domaine_principal= (Map<String,String>)content.get("s_DOMAINE_PRINCIPAL");
                this.domaine = (String) s_domaine_principal.get("label");

                Map<String,String> s_categorie= (Map<String,String>)content.get("s_CATEGORIE");

                if (s_categorie.get("label")== null)
                        this.categorie="Aucun";
                else
                        this.categorie = (String) s_categorie.get("label");



                Map<String,String> d_date_premiere_mep= (Map<String,String>)content.get("d_DATE_PREMIERE_MEP");
                if (d_date_premiere_mep.get("content")!=null)
                        this.dateMiseEnService = sourceFormat.parse(  d_date_premiere_mep.get("content"));

                Map<String,String> d_date_decommissionnement= (Map<String,String>)content.get("d_DATE_DECOMMISSIONNEMENT");
                if (d_date_decommissionnement.get("content")!=null)
                        this.dateDecommission = sourceFormat.parse(  d_date_decommissionnement.get("content"));

                Map<String,String> b_en_tma= (Map<String,String>)content.get("b_En_TMA");
                this.enTMA = (String) b_en_tma.get("label");
        }
}
