package sk.halmi.sittingorder.api.model;

/**
 * Created by FPTSlovakia on 7/12/2016.
 */
public class Miestnost_tono {
    private String cislo;
    private String poschodie;
    private String obsadenost;

    public Miestnost_tono(String cislo, String poschodie, String obsadenost, String kapacita) {
        this.obsadenost = obsadenost;
        this.kapacita = kapacita;
        this.cislo = cislo;
        this.poschodie = poschodie;
    }

    public String getObsadenost() {

        return obsadenost;
    }

    public void setObsadenost(String obsadenost) {
        this.obsadenost = obsadenost;
    }

    public String getKapacita() {
        return kapacita;
    }

    public void setKapacita(String kapacita) {
        this.kapacita = kapacita;
    }

    private String kapacita;

    public String getCislo() {
        return cislo;
    }

    public String getPoschodie() {
        return poschodie;
    }


    public Miestnost_tono(String cislo, String poschodie) {
        this.cislo = cislo;
        this.poschodie = poschodie;
    }

    @Override
    public String toString() {
        return "Miestnost{" +
                "cislo='" + cislo + '\'' +
                ", poschodie='" + poschodie + '\'' +
                '}';
    }
}


