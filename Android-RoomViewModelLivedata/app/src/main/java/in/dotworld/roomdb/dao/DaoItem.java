package in.dotworld.roomdb.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import in.dotworld.roomdb.model.Item;

@Dao
public interface DaoItem {

    @Insert
    void insert(Item item);


    @Query("SELECT * FROM details")
    public LiveData<List<Item>> getItems();

    @Query("SELECT * FROM details WHERE id = :id")
    public Item getItemById(int id);

}
