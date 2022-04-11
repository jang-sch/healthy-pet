package a7.mwisehart.mobiletodo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import java.util.List;

public class RecycleAdapter extends RecyclerView.Adapter<RecycleAdapter.ViewHolder>{
    private List<Item> items;
    private Context context;
    private OnItemListener onItemListener;

    public RecycleAdapter(Context context, List<Item> items, OnItemListener onItemListener){
        this.items = items;
        this.context = context;
        this.onItemListener = onItemListener;

    }



    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.recycler_row, parent, false);
        return new ViewHolder(view, onItemListener);

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.txtItem.setText(items.get(position).getDesc());

    }

    @Override
    public int getItemCount() {

        return items.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView txtItem;
        OnItemListener onItemListener;

        public ViewHolder(View itemView, OnItemListener onItemListener){
            super(itemView);
            this.onItemListener = onItemListener;
            txtItem = itemView.findViewById(R.id.txtItemView);
            itemView.setOnClickListener(this);

        }


        @Override
        public void onClick(View v) {
            onItemListener.onItemClicked(getAdapterPosition());

        }
    }
}
