package com.example.myapplication.adapter;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.model.Item;

import java.util.ArrayList;
import java.util.List;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ItemViewHolder> {

    public interface OnItemClickListener {
        void onItemClick(Item item);
        void onMarkResolved(Item item);
        void onDelete(Item item);
    }

    private final Context context;
    private List<Item> items = new ArrayList<>();
    private final boolean isManagementMode;
    private final OnItemClickListener listener;

    public ItemAdapter(Context context, boolean isManagementMode, OnItemClickListener listener) {
        this.context = context;
        this.isManagementMode = isManagementMode;
        this.listener = listener;
    }

    public void setItems(List<Item> items) {
        this.items = items != null ? items : new ArrayList<>();
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_card, parent, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        Item item = items.get(position);
        holder.bind(item);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    class ItemViewHolder extends RecyclerView.ViewHolder {
        ImageView imgItem;
        TextView tvTitle, tvTagType, tvCategory, tvLocation, tvDate;
        LinearLayout layoutManagementActions;
        Button btnMarkResolved, btnDelete;

        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            imgItem = itemView.findViewById(R.id.imgItem);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvTagType = itemView.findViewById(R.id.tvTagType);
            tvCategory = itemView.findViewById(R.id.tvCategory);
            tvLocation = itemView.findViewById(R.id.tvLocation);
            tvDate = itemView.findViewById(R.id.tvDate);
            layoutManagementActions = itemView.findViewById(R.id.layoutManagementActions);
            btnMarkResolved = itemView.findViewById(R.id.btnMarkResolved);
            btnDelete = itemView.findViewById(R.id.btnDelete);
        }

        public void bind(Item item) {
            tvTitle.setText(item.getTitle());
            tvCategory.setText(context.getString(R.string.category) + ": " + item.getCategory());
            tvLocation.setText(context.getString(R.string.location_label, item.getLocation()));
            tvDate.setText(context.getString(R.string.posted_on, item.getDate()));

            if ("RESOLVED".equalsIgnoreCase(item.getStatus())) {
                tvTagType.setText(R.string.status_resolved);
                tvTagType.setBackgroundColor(ContextCompat.getColor(context, R.color.tag_resolved));
            } else if ("FOUND".equalsIgnoreCase(item.getType())) {
                tvTagType.setText(R.string.type_found);
                tvTagType.setBackgroundColor(ContextCompat.getColor(context, R.color.tag_found));
            } else {
                tvTagType.setText(R.string.type_lost);
                tvTagType.setBackgroundColor(ContextCompat.getColor(context, R.color.tag_lost));
            }

            if (item.getImageUri() != null && !item.getImageUri().isEmpty()) {
                try {
                    imgItem.setImageURI(Uri.parse(item.getImageUri()));
                } catch (Exception e) {
                    imgItem.setImageResource(R.drawable.ic_image_placeholder);
                }
            } else {
                imgItem.setImageResource(R.drawable.ic_image_placeholder);
            }

            if (isManagementMode) {
                layoutManagementActions.setVisibility(View.VISIBLE);
                if ("RESOLVED".equalsIgnoreCase(item.getStatus())) {
                    btnMarkResolved.setEnabled(false);
                    btnMarkResolved.setText(R.string.status_resolved);
                } else {
                    btnMarkResolved.setEnabled(true);
                    btnMarkResolved.setText(R.string.mark_resolved);
                    btnMarkResolved.setOnClickListener(v -> {
                        if (listener != null) listener.onMarkResolved(item);
                    });
                }

                btnDelete.setOnClickListener(v -> {
                    if (listener != null) listener.onDelete(item);
                });
            } else {
                layoutManagementActions.setVisibility(View.GONE);
            }

            itemView.setOnClickListener(v -> {
                if (listener != null) listener.onItemClick(item);
            });
        }
    }
}