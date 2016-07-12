
package sk.halmi.sittingorder.api.model.room;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class RoomSet {

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
