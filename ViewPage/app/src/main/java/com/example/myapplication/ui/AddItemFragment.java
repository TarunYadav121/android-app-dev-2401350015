package com.example.myapplication.ui;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.myapplication.R;
import com.example.myapplication.db.DatabaseHelper;
import com.example.myapplication.model.Item;
import com.example.myapplication.utils.PreferenceManager;

import java.util.Calendar;
import java.util.Locale;

public class AddItemFragment extends Fragment {

    private RadioGroup rgItemType;
    private RadioButton rbLost, rbFound;
    private Spinner spinnerCategory;
    private EditText etTitle, etDescription, etLocation, etDate, etPhone, etEmail;
    private ImageView imgSelected;
    private Button btnSelectImage, btnSubmit;

    private DatabaseHelper dbHelper;
    private PreferenceManager prefManager;
    private Uri selectedImageUri = null;

    private final ActivityResultLauncher<String> imagePickerLauncher =
            registerForActivityResult(new ActivityResultContracts.GetContent(), uri -> {
                if (uri != null) {
                    selectedImageUri = uri;
                    imgSelected.setImageURI(uri);
                }
            });

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_item, container, false);

        rgItemType = view.findViewById(R.id.rgItemType);
        rbLost = view.findViewById(R.id.rbLost);
        rbFound = view.findViewById(R.id.rbFound);
        spinnerCategory = view.findViewById(R.id.spinnerCategory);
        etTitle = view.findViewById(R.id.etTitle);
        etDescription = view.findViewById(R.id.etDescription);
        etLocation = view.findViewById(R.id.etLocation);
        etDate = view.findViewById(R.id.etDate);
        etPhone = view.findViewById(R.id.etPhone);
        etEmail = view.findViewById(R.id.etEmail);
        imgSelected = view.findViewById(R.id.imgSelected);
        btnSelectImage = view.findViewById(R.id.btnSelectImage);
        btnSubmit = view.findViewById(R.id.btnSubmit);

        dbHelper = new DatabaseHelper(requireContext());
        prefManager = new PreferenceManager(requireContext());

        setupCategorySpinner();
        setupDatePicker();

        btnSelectImage.setOnClickListener(v -> imagePickerLauncher.launch("image/*"));
        btnSubmit.setOnClickListener(v -> submitItem());

        return view;
    }

    private void setupCategorySpinner() {
        String[] categories = new String[]{"Book", "Mobile", "ID Card", "Bottle", "Laptop", "Wallet", "Electronics", "Other"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_item, categories);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCategory.setAdapter(adapter);
    }

    private void setupDatePicker() {
        Calendar calendar = Calendar.getInstance();
        etDate.setText(String.format(Locale.getDefault(), "%d-%02d-%02d",
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH) + 1,
                calendar.get(Calendar.DAY_OF_MONTH)));

        etDate.setOnClickListener(v -> {
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH);
            int day = calendar.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog datePickerDialog = new DatePickerDialog(requireContext(),
                    (view, y, m, d) -> etDate.setText(String.format(Locale.getDefault(), "%d-%02d-%02d", y, m + 1, d)),
                    year, month, day);
            datePickerDialog.show();
        });
    }

    private void submitItem() {
        String title = etTitle.getText().toString().trim();
        String desc = etDescription.getText().toString().trim();
        String location = etLocation.getText().toString().trim();
        String date = etDate.getText().toString().trim();
        String phone = etPhone.getText().toString().trim();
        String email = etEmail.getText().toString().trim();

        if (title.isEmpty()) {
            etTitle.setError(getString(R.string.error_required));
            return;
        }
        if (desc.isEmpty()) {
            etDescription.setError(getString(R.string.error_required));
            return;
        }
        if (phone.isEmpty()) {
            etPhone.setError(getString(R.string.error_required));
            return;
        }

        String type = rbLost.isChecked() ? "LOST" : "FOUND";
        String category = spinnerCategory.getSelectedItem() != null ? spinnerCategory.getSelectedItem().toString() : "Other";
        String postedBy = prefManager.getStudentName();

        Item item = new Item();
        item.setTitle(title);
        item.setDescription(desc);
        item.setCategory(category);
        item.setType(type);
        item.setStatus("ACTIVE");
        item.setDate(date);
        item.setLocation(location.isEmpty() ? "Campus" : location);
        item.setContactName(postedBy);
        item.setContactPhone(phone);
        item.setContactEmail(email);
        item.setImageUri(selectedImageUri != null ? selectedImageUri.toString() : "");
        item.setPostedBy(postedBy);

        long id = dbHelper.insertItem(item);
        if (id > 0) {
            new AlertDialog.Builder(requireContext())
                    .setTitle(R.string.app_name)
                    .setMessage(R.string.post_success)
                    .setPositiveButton(R.string.ok, (dialog, which) -> clearForm())
                    .show();
        } else {
            Toast.makeText(requireContext(), "Failed to save post", Toast.LENGTH_SHORT).show();
        }
    }

    private void clearForm() {
        etTitle.setText("");
        etDescription.setText("");
        etLocation.setText("");
        etPhone.setText("");
        etEmail.setText("");
        selectedImageUri = null;
        imgSelected.setImageResource(R.drawable.ic_image_placeholder);
    }
}