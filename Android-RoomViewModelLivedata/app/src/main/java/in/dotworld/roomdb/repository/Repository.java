package in.dotworld.roomdb.repository;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

import in.dotworld.roomdb.dao.DaoItem;
import in.dotworld.roomdb.database.ItemDatabase;
import in.dotworld.roomdb.model.Item;

public class Repository {
        private DaoItem daoItem;
        private Repository repository;
        private ItemDatabase itemDatabase;
        public Repository(Application application){
            itemDatabase=ItemDatabase.getDatabase(application);
            daoItem=itemDatabase.getItemDao();
        }

        public LiveData<List<Item>> getItems(){
            return daoItem.getItems();
        }

        public void insert(Item item){
            new Repository.AsyncInsert(daoItem).execute(item);
        }

        public  class AsyncInsert extends AsyncTask<Item, Void, Void> {
            DaoItem daoItem;

            AsyncInsert(DaoItem dao){
            this.daoItem=dao;
            }

            @Override
            protected Void doInBackground(Item... items) {
                daoItem.insert(items[0]);
                return null;
            }
        }
}
