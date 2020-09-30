package in.dotworld.paging;


import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.paging.DataSource;
import androidx.paging.PageKeyedDataSource;

public class ItemDataSourcesFactory extends DataSource.Factory {

    public MutableLiveData<PageKeyedDataSource<Integer,Item>> itemLiveDataSources=new MutableLiveData<>();
    @NonNull
    @Override
    public DataSource create() {
        ItemDataSources itemDataSources=new ItemDataSources();
        itemLiveDataSources.postValue(itemDataSources);
        return itemDataSources;
    }

    public MutableLiveData<PageKeyedDataSource<Integer,Item>> getItemLiveDataSources()
    {
        return itemLiveDataSources;
    }
}

