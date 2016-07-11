
package sk.halmi.sittingorder.api.model;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Result {

    @SerializedName("__metadata")
    @Expose
    private Metadata metadata;
    @SerializedName("IdBuilding")
    @Expose
    private String idBuilding;
    @SerializedName("IdPerson")
    @Expose
    private Integer idPerson;
    @SerializedName("IdFloor")
    @Expose
    private String idFloor;
    @SerializedName("IdRoom")
    @Expose
    private String idRoom;
    @SerializedName("FirstName")
    @Expose
    private String firstName;
    @SerializedName("LastName")
    @Expose
    private String lastName;

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
     *     The idPerson
     */
    public Integer getIdPerson() {
        return idPerson;
    }

    /**
     * 
     * @param idPerson
     *     The IdPerson
     */
    public void setIdPerson(Integer idPerson) {
        this.idPerson = idPerson;
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
     *     The firstName
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * 
     * @param firstName
     *     The FirstName
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * 
     * @return
     *     The lastName
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * 
     * @param lastName
     *     The LastName
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

}
