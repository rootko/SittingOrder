package sk.halmi.sittingorder;

/**
 * Created by FPTSlovakia on 7/12/2016.
 */
public class Miestnost_tono {
    private String cislo;
    private String poschodie;

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


