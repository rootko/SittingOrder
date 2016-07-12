
package sk.halmi.sittingorder.api.model.room;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class Result {

    @SerializedName("__metadata")
    @Expose
    private Metadata metadata;
    @SerializedName("IdRoom")
    @Expose
    private String idRoom;
    @SerializedName("IdBuilding")
    @Expose
    private String idBuilding;
    @SerializedName("IdFloor")
    @Expose
    private String idFloor;
    @SerializedName("Capacity")
    @Expose
    private Integer capacity;
    @SerializedName("Description")
    @Expose
    private String description;
    @SerializedName("ToPersonSet")
    @Expose
    private ToPersonSet toPersonSet;

    /**
     * 
     * @return
     *     The metadata
     */
    public Metadata getMetadata() {
        return metadata;
    }

    /**
     * 
     * @param metadata
     *     The __metadata
     */
    public void setMetadata(Metadata metadata) {
        this.metadata = metadata;
    }

    /**
     * 
     * @return
     *     The idRoom
     */
    public String getIdRoom() {
        return idRoom;
    }

    /**
     * 
     * @param idRoom
     *     The IdRoom
     */
    public void setIdRoom(String idRoom) {
        this.idRoom = idRoom;
    }

    /**
     * 
     * @return
     *     The idBuilding
     */
    public String getIdBuilding() {
        return idBuilding;
    }

    /**
     * 
     * @param idBuilding
     *     The IdBuilding
     */
    public void setIdBuilding(String idBuilding) {
        this.idBuilding = idBuilding;
    }

    /**
     * 
     * @return
     *     The idFloor
     */
    public String getIdFloor() {
        return idFloor;
    }

    /**
     * 
     * @param idFloor
     *     The IdFloor
     */
    public void setIdFloor(String idFloor) {
        this.idFloor = idFloor;
    }

    /**
     * 
     * @return
     *     The capacity
     */
    public Integer getCapacity() {
        return capacity;
    }

    /**
     * 
     * @param capacity
     *     The Capacity
     */
    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
    }

    /**
     * 
     * @return
     *     The description
     */
    public String getDescription() {
        return description;
    }

    /**
     * 
     * @param description
     *     The Description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * 
     * @return
     *     The toPersonSet
     */
    public ToPersonSet getToPersonSet() {
        return toPersonSet;
    }

    /**
     * 
     * @param toPersonSet
     *     The ToPersonSet
     */
    public void setToPersonSet(ToPersonSet toPersonSet) {
        this.toPersonSet = toPersonSet;
    }

}
