package sk.halmi.sittingorder.api.model;

/**
 * Created by Lea user on 12/07/2016.
 */
public class RowItem {

    String idecko;
    String meno;
    String priezvisko;
    String budova;
    String poschodie;
    String miestnost;

    public RowItem(String idecko,String meno, String priezvisko, String budova, String poschodie, String miestnost)
    {
        this.idecko=idecko;
        this.meno=meno;
        this.priezvisko=priezvisko;
        this.budova=budova;
        this.poschodie=poschodie;
        this.miestnost=miestnost;
    }

    public String getIdecko() { return idecko; }

    public String getMeno() {
        return meno;
    }

    public String getPriezvisko() {
        return priezvisko;
    }

    public String getBudova() {
        return budova;
    }

    public String getPoschodie() {
        return poschodie;
    }

    public String getMiestnost() {
        return miestnost;
    }
}
