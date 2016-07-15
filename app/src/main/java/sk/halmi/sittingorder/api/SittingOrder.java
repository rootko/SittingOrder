package sk.halmi.sittingorder.api;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;
import sk.halmi.sittingorder.api.model.person.PersonSet;
import sk.halmi.sittingorder.api.model.putperson.Person;
import sk.halmi.sittingorder.api.model.putperson.PutPerson;
import sk.halmi.sittingorder.api.model.room.RoomSet;

/**
 * Created by FS0149 on 7.7.2016.
 */
public interface SittingOrder {
	@GET("/sap/opu/odata/SAP/ZSUMMER_SRV/PersonSet")
	Call<PersonSet> getPersons(
			@Query("$filter") String nameFilter,
			@Query("$format") String value
	);

	@GET("/sap/opu/odata/SAP/ZSUMMER_SRV/RoomSet")
	Call<RoomSet> getRooms(
			@Query("$filter") String nameFilter,
			@Query("$format") String value

	);

	@Headers("X-Requested-With: XMLHttpRequest")
	@POST("/sap/opu/odata/SAP/ZSUMMER_SRV/PersonSet")
	Call<Person> putPerson(
			@Body Person person
	);

	@GET("/sap/opu/odata/SAP/ZSUMMER_SRV/PersonSet({id})")
	Call<PutPerson> getPerson(
			@Path("id") Integer id,
			@Query("$format") String value
	);


}
