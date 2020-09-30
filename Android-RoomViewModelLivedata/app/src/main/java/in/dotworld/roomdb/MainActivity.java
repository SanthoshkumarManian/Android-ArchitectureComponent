package in.dotworld.roomdb;


import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import in.dotworld.roomdb.model.Item;
import in.dotworld.roomdb.viewmodel.ItemViewModel;


public class MainActivity extends AppCompatActivity {


    private EditText et1,et2;
    private Button button;
    private TextView t1;
    private ItemViewModel itemViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        itemViewModel=ViewModelProviders.of(this).get(ItemViewModel.class);
        button=findViewById(R.id.fab);
        et1=findViewById(R.id.edit1);
        et2=findViewById(R.id.edit2);
        t1=findViewById(R.id.textView);
        et1.setText("");
        et2.setText(" ");

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int id=Integer.valueOf(et1.getText().toString());
                String name=et2.getText().toString();
                Item item = new Item(id,name);
                itemViewModel.insert(item);

            }
        });

        itemViewModel.getItems().observe(this, new Observer<List<Item>>() {
            @Override
            public void onChanged(List<Item> items) {
                t1.setText(items.get(0).getId()+"  "+items.get(0).getFirsname());
            }
        });
    }
}
