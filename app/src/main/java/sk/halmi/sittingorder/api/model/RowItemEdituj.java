package sk.halmi.sittingorder.api.model;

/**
 * Created by Lea user on 12/07/2016.
 */
public class RowItemEdituj {
    String meno;
    String priezvisko;

    public RowItemEdituj(String meno, String priezvisko)
    {
        this.meno=meno;
        this.priezvisko=priezvisko;
    }

    public String getMeno() {
        return meno;
    }

    public String getPriezvisko() {
        return priezvisko;
    }
}
