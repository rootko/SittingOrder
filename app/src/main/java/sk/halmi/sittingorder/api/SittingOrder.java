package sk.halmi.sittingorder.api;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import sk.halmi.sittingorder.api.model.person.PersonSet;
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

}
