package com.example.myapplication.ui;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.DetailActivity;
import com.example.myapplication.R;
import com.example.myapplication.adapter.ItemAdapter;
import com.example.myapplication.db.DatabaseHelper;
import com.example.myapplication.model.Item;
import com.example.myapplication.utils.PreferenceManager;

import java.util.List;

public class MyItemsFragment extends Fragment implements ItemAdapter.OnItemClickListener {

    private TextView tvEmptyMyItems;
    private RecyclerView recyclerViewMyItems;

    private DatabaseHelper dbHelper;
    private PreferenceManager prefManager;
    private ItemAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my_items, container, false);

        tvEmptyMyItems = view.findViewById(R.id.tvEmptyMyItems);
        recyclerViewMyItems = view.findViewById(R.id.recyclerViewMyItems);

        dbHelper = new DatabaseHelper(requireContext());
        prefManager = new PreferenceManager(requireContext());

        setupRecyclerView();

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        loadMyItems();
    }

    private void setupRecyclerView() {
        adapter = new ItemAdapter(requireContext(), true, this);
        recyclerViewMyItems.setLayoutManager(new LinearLayoutManager(requireContext()));
        recyclerViewMyItems.setAdapter(adapter);
    }

    private void loadMyItems() {
        String studentName = prefManager.getStudentName();
        List<Item> myItems = dbHelper.getMyItems(studentName);

        if (myItems.isEmpty()) {
            tvEmptyMyItems.setVisibility(View.VISIBLE);
            recyclerViewMyItems.setVisibility(View.GONE);
        } else {
            tvEmptyMyItems.setVisibility(View.GONE);
            recyclerViewMyItems.setVisibility(View.VISIBLE);
            adapter.setItems(myItems);
        }
    }

    @Override
    public void onItemClick(Item item) {
        Intent intent = new Intent(requireContext(), DetailActivity.class);
        intent.putExtra("item_data", item);
        startActivity(intent);
    }

    @Override
    public void onMarkResolved(Item item) {
        dbHelper.updateItemStatus(item.getId(), "RESOLVED");
        Toast.makeText(requireContext(), R.string.status_resolved, Toast.LENGTH_SHORT).show();
        loadMyItems();
    }

    @Override
    public void onDelete(Item item) {
        new AlertDialog.Builder(requireContext())
                .setTitle(R.string.confirm_delete_title)
                .setMessage(R.string.confirm_delete_msg)
                .setPositiveButton(R.string.delete_item, (dialog, which) -> {
                    dbHelper.deleteItem(item.getId());
                    Toast.makeText(requireContext(), "Item deleted", Toast.LENGTH_SHORT).show();
                    loadMyItems();
                })
                .setNegativeButton(R.string.cancel, null)
                .show();
    }
}