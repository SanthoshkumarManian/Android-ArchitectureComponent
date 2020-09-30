package in.dotworld.roomdb.model;

import android.widget.TextView;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "details")
public class Item {

    @PrimaryKey
    private int id;

    private String firsname;

    public Item(int id, String firsname) {
        this.id = id;
        this.firsname = firsname;
    }

    public int getId() {
        return this.id;
    }

    public String getFirsname() {
        return this.firsname;
    }

}
