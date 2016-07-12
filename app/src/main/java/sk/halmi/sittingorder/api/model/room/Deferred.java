
package sk.halmi.sittingorder.api.model.room;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class Deferred {

    @SerializedName("uri")
    @Expose
    private String uri;

    /**
     * 
     * @return
     *     The uri
     */
    public String getUri() {
        return uri;
    }

    /**
     * 
     * @param uri
     *     The uri
     */
    public void setUri(String uri) {
        this.uri = uri;
    }

}
