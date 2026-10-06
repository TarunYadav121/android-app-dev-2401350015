package com.example.myapplication.ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.DetailActivity;
import com.example.myapplication.MainActivity;
import com.example.myapplication.R;
import com.example.myapplication.adapter.ItemAdapter;
import com.example.myapplication.db.DatabaseHelper;
import com.example.myapplication.model.Item;
import com.example.myapplication.utils.PreferenceManager;
import com.google.android.material.chip.ChipGroup;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class HomeFragment extends Fragment implements ItemAdapter.OnItemClickListener {

    private TextView tvWelcomeMsg, tvCountLost, tvCountFound, tvCountResolved, tvEmpty;
    private EditText etSearch;
    private ChipGroup chipGroupFilter;
    private Spinner spinnerCategoryFilter;
    private ProgressBar progressBar;
    private RecyclerView recyclerViewItems;
    private FloatingActionButton fabAddItem;

    private DatabaseHelper dbHelper;
    private PreferenceManager prefManager;
    private ItemAdapter adapter;

    private String currentTypeFilter = "ALL";
    private String currentCategoryFilter = "ALL";
    private String searchQuery = "";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        tvWelcomeMsg = view.findViewById(R.id.tvWelcomeMsg);
        tvCountLost = view.findViewById(R.id.tvCountLost);
        tvCountFound = view.findViewById(R.id.tvCountFound);
        tvCountResolved = view.findViewById(R.id.tvCountResolved);
        tvEmpty = view.findViewById(R.id.tvEmpty);
        etSearch = view.findViewById(R.id.etSearch);
        chipGroupFilter = view.findViewById(R.id.chipGroupFilter);
        spinnerCategoryFilter = view.findViewById(R.id.spinnerCategoryFilter);
        progressBar = view.findViewById(R.id.progressBar);
        recyclerViewItems = view.findViewById(R.id.recyclerViewItems);
        fabAddItem = view.findViewById(R.id.fabAddItem);

        dbHelper = new DatabaseHelper(requireContext());
        prefManager = new PreferenceManager(requireContext());

        setupRecyclerView();
        setupCategorySpinner();
        setupFilters();
        setupSearch();

        fabAddItem.setOnClickListener(v -> {
            if (getActivity() instanceof MainActivity) {
                ((MainActivity) getActivity()).navigateToFragment(new AddItemFragment(), R.id.nav_add);
            }
        });

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        updateWelcomeHeader();
        loadCounts();
        loadData();
    }

    private void updateWelcomeHeader() {
        String studentName = prefManager.getStudentName();
        tvWelcomeMsg.setText(getString(R.string.welcome_message) + "\n" + studentName);
    }

    private void loadCounts() {
        int[] counts = dbHelper.getItemCounts();
        tvCountLost.setText(String.valueOf(counts[0]));
        tvCountFound.setText(String.valueOf(counts[1]));
        tvCountResolved.setText(String.valueOf(counts[2]));
    }

    private void setupRecyclerView() {
        adapter = new ItemAdapter(requireContext(), false, this);
        recyclerViewItems.setLayoutManager(new LinearLayoutManager(requireContext()));
        recyclerViewItems.setAdapter(adapter);
    }

    private void setupCategorySpinner() {
        String[] categories = new String[]{"ALL Categories", "Book", "Mobile", "ID Card", "Bottle", "Laptop", "Wallet", "Electronics", "Other"};
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_item, categories);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCategoryFilter.setAdapter(spinnerAdapter);

        spinnerCategoryFilter.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                currentCategoryFilter = position == 0 ? "ALL" : categories[position];
                loadData();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });
    }

    private void setupFilters() {
        chipGroupFilter.setOnCheckedChangeListener((group, checkedId) -> {
            if (checkedId == R.id.chipLost) {
                currentTypeFilter = "LOST";
            } else if (checkedId == R.id.chipFound) {
                currentTypeFilter = "FOUND";
            } else {
                currentTypeFilter = "ALL";
            }
            loadData();
        });
    }

    private void setupSearch() {
        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                searchQuery = s.toString();
                loadData();
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });
    }

    private void loadData() {
        progressBar.setVisibility(View.VISIBLE);
        recyclerViewItems.setVisibility(View.GONE);
        tvEmpty.setVisibility(View.GONE);

        new Handler(Looper.getMainLooper()).postDelayed(() -> {
            if (!isAdded()) return;
            List<Item> items = dbHelper.searchItems(searchQuery, currentTypeFilter, currentCategoryFilter);
            progressBar.setVisibility(View.GONE);

            if (items.isEmpty()) {
                tvEmpty.setVisibility(View.VISIBLE);
                recyclerViewItems.setVisibility(View.GONE);
            } else {
                tvEmpty.setVisibility(View.GONE);
                recyclerViewItems.setVisibility(View.VISIBLE);
                adapter.setItems(items);
            }
        }, 300);
    }

    @Override
    public void onItemClick(Item item) {
        Intent intent = new Intent(requireContext(), DetailActivity.class);
        intent.putExtra("item_data", item);
        startActivity(intent);
    }

    @Override
    public void onMarkResolved(Item item) {}

    @Override
    public void onDelete(Item item) {}
}