
package sk.halmi.sittingorder.api.model.person;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PersonSet {

    @SerializedName("d")
    @Expose
    private D d;

    /**
     * 
     * @return
     *     The d
     */
    public D getD() {
        return d;
    }

    /**
     * 
     * @param d
     *     The d
     */
    public void setD(D d) {
        this.d = d;
    }

}
