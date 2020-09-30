package in.dotworld.roomdb.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import in.dotworld.roomdb.model.Item;
import in.dotworld.roomdb.repository.Repository;

public class ItemViewModel extends AndroidViewModel {
    Repository repository;
    public ItemViewModel(Application application) {
        super(application);
        repository=new Repository(application);
    }

    public void insert(Item item){
        repository.insert(item);
    }
    public LiveData<List<Item>> getItems(){
        return repository.getItems();
    }
}
