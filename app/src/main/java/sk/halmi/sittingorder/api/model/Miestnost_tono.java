package sk.halmi.sittingorder.api.model;

/**
 * Created by FPTSlovakia on 7/12/2016.
 */
public class Miestnost_tono {
    private String building;
    private String cislo;
    private String poschodie;
    private String obsadenost;

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


    @Override
    public String toString() {
        return "Miestnost{" +
                "cislo='" + cislo + '\'' +
                ", poschodie='" + poschodie + '\'' +
                '}';
    }

    public String getBuilding() {
        return building;
    }

    public void setBuilding(String building) {
        this.building = building;
    }

    public Miestnost_tono(String building, String poschodie, String cislo, String obsadenost, String kapacita) {
        this.building = building;
        this.cislo = cislo;
        this.poschodie = poschodie;
        this.obsadenost = obsadenost;
        this.kapacita = kapacita;
    }
}


