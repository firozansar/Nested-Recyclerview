package info.firozansari.nested_recyclerview.helper;

public interface Callback<D> {

    public abstract void onFailure(Exception ex);

    public abstract void onSuccess(D result);
}
