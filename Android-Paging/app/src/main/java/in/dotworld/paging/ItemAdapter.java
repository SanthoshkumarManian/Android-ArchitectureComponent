package in.dotworld.paging;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.paging.PagedListAdapter;
import androidx.recyclerview.widget.AsyncDifferConfig;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

public class ItemAdapter extends PagedListAdapter<Item,ItemAdapter.ItemViewHolder> {

    private Context mcontext;
    protected ItemAdapter(Context context) {
        super(DIFF_CALLBACK);
        this.mcontext=context;
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       View view = LayoutInflater.from(mcontext).inflate(R.layout.recyclerview,parent,false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        Item item=getItem(position);
        if(item!=null)
        {
            Glide.with(mcontext)
                    .load(item.owner.profile_image)
                    .into(holder.imageView);
            holder.textView.setText(item.owner.display_name);
        }else
        {
            Toast.makeText(mcontext,"null",Toast.LENGTH_LONG).show();
        }
    }
    private static DiffUtil.ItemCallback<Item> DIFF_CALLBACK=new DiffUtil.ItemCallback<Item>() {
        @Override
        public boolean areItemsTheSame(@NonNull Item oldItem, @NonNull Item newItem) {
            return oldItem.answer_id==newItem.answer_id;
        }


        @SuppressLint("DiffUtilEquals")
        @Override
        public boolean areContentsTheSame(Item oldItem,  Item newItem) {
            return oldItem.equals(newItem);
        }
    };

    class ItemViewHolder extends RecyclerView.ViewHolder{

        ImageView imageView;
        TextView textView;
        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView=itemView.findViewById(R.id.imageview);
            textView=itemView.findViewById(R.id.textview);
        }
    }

}
