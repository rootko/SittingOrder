
package sk.halmi.sittingorder.api.model.room;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class ToPersonSet {

    @SerializedName("__deferred")
    @Expose
    private Deferred deferred;

    /**
     * 
     * @return
     *     The deferred
     */
    public Deferred getDeferred() {
        return deferred;
    }

    /**
     * 
     * @param deferred
     *     The __deferred
     */
    public void setDeferred(Deferred deferred) {
        this.deferred = deferred;
    }

}
