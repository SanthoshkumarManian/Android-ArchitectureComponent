package in.dotworld.roomdb.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import in.dotworld.roomdb.dao.DaoItem;
import in.dotworld.roomdb.model.Item;

@Database(entities = {Item.class},version = 1,exportSchema = false)
public abstract class ItemDatabase extends RoomDatabase {

    public static ItemDatabase INSTANCE;
    public abstract DaoItem getItemDao();

    public  static ItemDatabase getDatabase(Context context){
        if(INSTANCE == null){
            synchronized (ItemDatabase.class) {

                INSTANCE = Room.databaseBuilder(context, ItemDatabase.class, "mydb")
                        .allowMainThreadQueries()
                        .build();
            }
                }
        return INSTANCE;
        }
}
