package in.dotworld.paging;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.paging.PageKeyedDataSource;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ItemDataSources extends PageKeyedDataSource<Integer ,Item> {

    public  static final int PAGE_SiZE=50;
    public  static final int FIRST_PAGE=1;
    public  static final String ORDER="asc";
    public  static final String SORT="activity";
    public  static final  String PAGE_NAME="stackoverflow";


    @Override
    public void loadInitial(@NonNull final LoadInitialParams<Integer> params, @NonNull final LoadInitialCallback<Integer, Item> callback) {

        RetrofitClient.getInstance()
                .getapi()
                .getAnswers(FIRST_PAGE,PAGE_SiZE,ORDER,SORT,PAGE_NAME)
                .enqueue(new Callback<StackResponse>() {
                    @Override
                    public void onResponse(Call<StackResponse> call,Response<StackResponse> response) {
                        StackResponse stackResponse=response.body();
                        if (stackResponse!= null)
                             callback.onResult(response.body().items, null, FIRST_PAGE + 1);
                    }
                    @Override
                    public void onFailure(Call<StackResponse> call, Throwable t) {

                    }
                });

    }

    @Override
    public void loadBefore(@NonNull final LoadParams<Integer> params, @NonNull final LoadCallback<Integer, Item> callback) {

        RetrofitClient.getInstance().getapi().getAnswers(params.key,PAGE_SiZE,ORDER,SORT,PAGE_NAME)
                .enqueue(new Callback<StackResponse>() {
                    @Override
                    public void onResponse(Call<StackResponse> call, Response<StackResponse> response) {
                        Integer key=(params.key>1)?params.key-1:null;
                        if(response.body()!=null) {
                            callback.onResult(response.body().items,key);
                        }
                    }

                    @Override
                    public void onFailure(Call<StackResponse> call, Throwable t) {

                    }
                });
    }

    @Override
    public void loadAfter(@NonNull final LoadParams<Integer> params, @NonNull final LoadCallback<Integer, Item> callback) {

        RetrofitClient.getInstance()
                .getapi()
                .getAnswers(params.key,PAGE_SiZE,ORDER,SORT,PAGE_NAME)
                .enqueue(new Callback<StackResponse>() {
                    @Override
                    public void onResponse(Call<StackResponse> call, Response<StackResponse> response) {

                        if(response.body()!=null) {
                            Integer key=response.body().has_more?params.key+1:null;
                            callback.onResult(response.body().items,key);
                        }
                        }

                    @Override
                    public void onFailure(Call<StackResponse> call, Throwable t) {

                    }
                });
    }
}
