package sk.halmi.sittingorder.api.model.putperson;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by FS0149 on 15.7.2016.
 */
public class Person {
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

	public Person() {
	}

	public Person(String idBuilding, Integer idPerson, String idFloor, String idRoom, String firstName, String lastName) {
		this.idBuilding = idBuilding;
		this.idPerson = idPerson;
		this.idFloor = idFloor;
		this.idRoom = idRoom;
		this.firstName = firstName;
		this.lastName = lastName;
	}

	public String getIdBuilding() {
		return idBuilding;
	}

	public void setIdBuilding(String idBuilding) {
		this.idBuilding = idBuilding;
	}

	public Integer getIdPerson() {
		return idPerson;
	}

	public void setIdPerson(Integer idPerson) {
		this.idPerson = idPerson;
	}

	public String getIdFloor() {
		return idFloor;
	}

	public void setIdFloor(String idFloor) {
		this.idFloor = idFloor;
	}

	public String getIdRoom() {
		return idRoom;
	}

	public void setIdRoom(String idRoom) {
		this.idRoom = idRoom;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
}
