package com.example.myapplication;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.example.myapplication.model.Item;
import com.google.android.material.appbar.MaterialToolbar;

public class DetailActivity extends AppCompatActivity {

    private MaterialToolbar toolbarDetail;
    private ImageView imgDetail;
    private TextView tvDetailTitle, tvDetailTagType, tvDetailCategory, tvDetailLocation, tvDetailDate;
    private TextView tvDetailDescription, tvContactName, tvContactPhone, tvContactEmail;
    private Button btnCall, btnEmail, btnShare;

    private Item currentItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        toolbarDetail = findViewById(R.id.toolbarDetail);
        imgDetail = findViewById(R.id.imgDetail);
        tvDetailTitle = findViewById(R.id.tvDetailTitle);
        tvDetailTagType = findViewById(R.id.tvDetailTagType);
        tvDetailCategory = findViewById(R.id.tvDetailCategory);
        tvDetailLocation = findViewById(R.id.tvDetailLocation);
        tvDetailDate = findViewById(R.id.tvDetailDate);
        tvDetailDescription = findViewById(R.id.tvDetailDescription);
        tvContactName = findViewById(R.id.tvContactName);
        tvContactPhone = findViewById(R.id.tvContactPhone);
        tvContactEmail = findViewById(R.id.tvContactEmail);
        btnCall = findViewById(R.id.btnCall);
        btnEmail = findViewById(R.id.btnEmail);
        btnShare = findViewById(R.id.btnShare);

        toolbarDetail.setNavigationOnClickListener(v -> finish());

        if (getIntent() != null && getIntent().hasExtra("item_data")) {
            currentItem = (Item) getIntent().getSerializableExtra("item_data");
            displayDetails();
        } else {
            Toast.makeText(this, "Item details unavailable", Toast.LENGTH_SHORT).show();
            finish();
        }

        btnCall.setOnClickListener(v -> makePhoneCall());
        btnEmail.setOnClickListener(v -> sendEmail());
        btnShare.setOnClickListener(v -> shareItem());
    }

    private void displayDetails() {
        if (currentItem == null) return;

        tvDetailTitle.setText(currentItem.getTitle());
        tvDetailCategory.setText(getString(R.string.category) + ": " + currentItem.getCategory());
        tvDetailLocation.setText(getString(R.string.location_label, currentItem.getLocation()));
        tvDetailDate.setText(getString(R.string.posted_on, currentItem.getDate()));
        tvDetailDescription.setText(currentItem.getDescription());

        tvContactName.setText("Name: " + currentItem.getContactName());
        tvContactPhone.setText("Phone: " + currentItem.getContactPhone());
        tvContactEmail.setText("Email: " + currentItem.getContactEmail());

        if ("RESOLVED".equalsIgnoreCase(currentItem.getStatus())) {
            tvDetailTagType.setText(R.string.status_resolved);
            tvDetailTagType.setBackgroundColor(ContextCompat.getColor(this, R.color.tag_resolved));
        } else if ("FOUND".equalsIgnoreCase(currentItem.getType())) {
            tvDetailTagType.setText(R.string.type_found);
            tvDetailTagType.setBackgroundColor(ContextCompat.getColor(this, R.color.tag_found));
        } else {
            tvDetailTagType.setText(R.string.type_lost);
            tvDetailTagType.setBackgroundColor(ContextCompat.getColor(this, R.color.tag_lost));
        }

        if (currentItem.getImageUri() != null && !currentItem.getImageUri().isEmpty()) {
            try {
                imgDetail.setImageURI(Uri.parse(currentItem.getImageUri()));
            } catch (Exception e) {
                imgDetail.setImageResource(R.drawable.ic_image_placeholder);
            }
        } else {
            imgDetail.setImageResource(R.drawable.ic_image_placeholder);
        }
    }

    private void makePhoneCall() {
        if (currentItem == null || currentItem.getContactPhone() == null || currentItem.getContactPhone().isEmpty()) {
            Toast.makeText(this, "Phone number not available", Toast.LENGTH_SHORT).show();
            return;
        }
        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse("tel:" + currentItem.getContactPhone()));
        startActivity(intent);
    }

    private void sendEmail() {
        if (currentItem == null || currentItem.getContactEmail() == null || currentItem.getContactEmail().isEmpty()) {
            Toast.makeText(this, "Email address not available", Toast.LENGTH_SHORT).show();
            return;
        }
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:" + currentItem.getContactEmail()));
        intent.putExtra(Intent.EXTRA_SUBJECT, "Regarding Campus Lost & Found Item: " + currentItem.getTitle());
        startActivity(intent);
    }

    private void shareItem() {
        if (currentItem == null) return;
        String text = getString(R.string.share_text, currentItem.getTitle(), currentItem.getLocation(), currentItem.getContactPhone());
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, text);
        sendIntent.setType("text/plain");
        startActivity(Intent.createChooser(sendIntent, getString(R.string.action_share)));
    }
}