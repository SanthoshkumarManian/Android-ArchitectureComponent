package in.dotworld.paging;

import android.app.Application;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PageKeyedDataSource;
import androidx.paging.PagedList;

public class ItemViewModel extends ViewModel {

     LiveData<PagedList<Item>> itemPagedList;
     LiveData<PageKeyedDataSource<Integer,Item>> liveDataSources;

    public ItemViewModel(){

        ItemDataSourcesFactory itemDataSourcesFactory=new ItemDataSourcesFactory();
        liveDataSources=itemDataSourcesFactory.getItemLiveDataSources();
        PagedList.Config pagedlist=new PagedList.Config.Builder()
                .setEnablePlaceholders(false)
                .setPageSize(ItemDataSources.PAGE_SiZE)
                .build();
        itemPagedList=new LivePagedListBuilder(itemDataSourcesFactory,pagedlist).build();
    }
}
