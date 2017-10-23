package info.firozansari.nested_recyclerview.helper;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * @author Firoz Ansari
 */
public abstract class RetrofitCallback<T> implements Callback<T> {

    @Override
    public void onResponse(Call<T> call, Response<T> response) {

    }

    @Override
    public void onFailure(Call<T> call, Throwable t) {

    }
}
