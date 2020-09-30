package in.dotworld.paging;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface API {

   @GET("answers")
    Call<StackResponse> getAnswers(
            @Query("page") int page,
            @Query("pagesize") int size,
            @Query("order")String order,
            @Query("sort")String sort,
            @Query("site")String site

   );


}
