package com.example.myapplication.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.myapplication.model.Item;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "CampusLostFound.db";
    private static final int DATABASE_VERSION = 1;

    public static final String TABLE_ITEMS = "items";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_TITLE = "title";
    public static final String COLUMN_DESCRIPTION = "description";
    public static final String COLUMN_CATEGORY = "category";
    public static final String COLUMN_TYPE = "type"; // LOST / FOUND
    public static final String COLUMN_STATUS = "status"; // ACTIVE / RESOLVED
    public static final String COLUMN_DATE = "date";
    public static final String COLUMN_LOCATION = "location";
    public static final String COLUMN_CONTACT_NAME = "contact_name";
    public static final String COLUMN_CONTACT_PHONE = "contact_phone";
    public static final String COLUMN_CONTACT_EMAIL = "contact_email";
    public static final String COLUMN_IMAGE_URI = "image_uri";
    public static final String COLUMN_POSTED_BY = "posted_by";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE = "CREATE TABLE " + TABLE_ITEMS + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUMN_TITLE + " TEXT, "
                + COLUMN_DESCRIPTION + " TEXT, "
                + COLUMN_CATEGORY + " TEXT, "
                + COLUMN_TYPE + " TEXT, "
                + COLUMN_STATUS + " TEXT, "
                + COLUMN_DATE + " TEXT, "
                + COLUMN_LOCATION + " TEXT, "
                + COLUMN_CONTACT_NAME + " TEXT, "
                + COLUMN_CONTACT_PHONE + " TEXT, "
                + COLUMN_CONTACT_EMAIL + " TEXT, "
                + COLUMN_IMAGE_URI + " TEXT, "
                + COLUMN_POSTED_BY + " TEXT" + ")";
        db.execSQL(CREATE_TABLE);

        seedSampleData(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ITEMS);
        onCreate(db);
    }

    private void seedSampleData(SQLiteDatabase db) {
        insertSample(db, "Blue Data Structure Book", "Data Structures & Algorithms Java Edition left near desk 12", "Book", "LOST", "ACTIVE", "2026-09-28", "Main Library 2nd Floor", "Rahul Sharma", "9876543210", "rahul@campus.edu", "", "Rahul Sharma");
        insertSample(db, "Black Leather Wallet", "Contains Student ID and ATM card found near canteen table", "Wallet", "FOUND", "ACTIVE", "2026-09-29", "Student Canteen", "Priya Verma", "9876501234", "priya@campus.edu", "", "Priya Verma");
        insertSample(db, "Water Bottle (Hydro Flask)", "Blue metallic water bottle with stickers", "Bottle", "LOST", "RESOLVED", "2026-09-25", "Sports Complex", "Student", "9123456789", "student@campus.edu", "", "Student");
        insertSample(db, "Dell Laptop Charger", "65W USB-C charger left plugged in Lab 3", "Electronics", "FOUND", "ACTIVE", "2026-09-29", "Computer Lab 3", "Aman Gupta", "9988776655", "aman@campus.edu", "", "Aman Gupta");
    }

    private void insertSample(SQLiteDatabase db, String title, String desc, String cat, String type, String status, String date, String loc, String name, String phone, String email, String img, String postedBy) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_TITLE, title);
        values.put(COLUMN_DESCRIPTION, desc);
        values.put(COLUMN_CATEGORY, cat);
        values.put(COLUMN_TYPE, type);
        values.put(COLUMN_STATUS, status);
        values.put(COLUMN_DATE, date);
        values.put(COLUMN_LOCATION, loc);
        values.put(COLUMN_CONTACT_NAME, name);
        values.put(COLUMN_CONTACT_PHONE, phone);
        values.put(COLUMN_CONTACT_EMAIL, email);
        values.put(COLUMN_IMAGE_URI, img);
        values.put(COLUMN_POSTED_BY, postedBy);
        db.insert(TABLE_ITEMS, null, values);
    }

    public long insertItem(Item item) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_TITLE, item.getTitle());
        values.put(COLUMN_DESCRIPTION, item.getDescription());
        values.put(COLUMN_CATEGORY, item.getCategory());
        values.put(COLUMN_TYPE, item.getType());
        values.put(COLUMN_STATUS, item.getStatus() != null ? item.getStatus() : "ACTIVE");
        values.put(COLUMN_DATE, item.getDate());
        values.put(COLUMN_LOCATION, item.getLocation());
        values.put(COLUMN_CONTACT_NAME, item.getContactName());
        values.put(COLUMN_CONTACT_PHONE, item.getContactPhone());
        values.put(COLUMN_CONTACT_EMAIL, item.getContactEmail());
        values.put(COLUMN_IMAGE_URI, item.getImageUri());
        values.put(COLUMN_POSTED_BY, item.getPostedBy());

        long id = db.insert(TABLE_ITEMS, null, values);
        db.close();
        return id;
    }

    public List<Item> getAllItems() {
        return searchItems("", "ALL", "ALL");
    }

    public List<Item> searchItems(String query, String typeFilter, String categoryFilter) {
        List<Item> list = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        StringBuilder sql = new StringBuilder("SELECT * FROM " + TABLE_ITEMS + " WHERE 1=1");
        List<String> selectionArgs = new ArrayList<>();

        if (query != null && !query.trim().isEmpty()) {
            sql.append(" AND (").append(COLUMN_TITLE).append(" LIKE ? OR ")
               .append(COLUMN_DESCRIPTION).append(" LIKE ? OR ")
               .append(COLUMN_LOCATION).append(" LIKE ?)");
            String q = "%" + query.trim() + "%";
            selectionArgs.add(q);
            selectionArgs.add(q);
            selectionArgs.add(q);
        }

        if (typeFilter != null && !typeFilter.equalsIgnoreCase("ALL")) {
            sql.append(" AND ").append(COLUMN_TYPE).append(" = ?");
            selectionArgs.add(typeFilter);
        }

        if (categoryFilter != null && !categoryFilter.equalsIgnoreCase("ALL")) {
            sql.append(" AND ").append(COLUMN_CATEGORY).append(" = ?");
            selectionArgs.add(categoryFilter);
        }

        sql.append(" ORDER BY ").append(COLUMN_ID).append(" DESC");

        Cursor cursor = db.rawQuery(sql.toString(), selectionArgs.toArray(new String[0]));
        if (cursor.moveToFirst()) {
            do {
                Item item = cursorToItem(cursor);
                list.add(item);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return list;
    }

    public List<Item> getMyItems(String postedBy) {
        List<Item> list = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_ITEMS, null, COLUMN_POSTED_BY + "=? OR " + COLUMN_POSTED_BY + "=?",
                new String[]{postedBy, "Student"}, null, null, COLUMN_ID + " DESC");

        if (cursor.moveToFirst()) {
            do {
                list.add(cursorToItem(cursor));
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return list;
    }

    public void updateItemStatus(long id, String status) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_STATUS, status);
        db.update(TABLE_ITEMS, values, COLUMN_ID + "=?", new String[]{String.valueOf(id)});
        db.close();
    }

    public void deleteItem(long id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_ITEMS, COLUMN_ID + "=?", new String[]{String.valueOf(id)});
        db.close();
    }

    public int[] getItemCounts() {
        int[] counts = new int[3]; // [lostCount, foundCount, resolvedCount]
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor c1 = db.rawQuery("SELECT COUNT(*) FROM " + TABLE_ITEMS + " WHERE " + COLUMN_TYPE + "='LOST' AND " + COLUMN_STATUS + "='ACTIVE'", null);
        if (c1.moveToFirst()) counts[0] = c1.getInt(0);
        c1.close();

        Cursor c2 = db.rawQuery("SELECT COUNT(*) FROM " + TABLE_ITEMS + " WHERE " + COLUMN_TYPE + "='FOUND' AND " + COLUMN_STATUS + "='ACTIVE'", null);
        if (c2.moveToFirst()) counts[1] = c2.getInt(0);
        c2.close();

        Cursor c3 = db.rawQuery("SELECT COUNT(*) FROM " + TABLE_ITEMS + " WHERE " + COLUMN_STATUS + "='RESOLVED'", null);
        if (c3.moveToFirst()) counts[2] = c3.getInt(0);
        c3.close();

        db.close();
        return counts;
    }

    private Item cursorToItem(Cursor cursor) {
        Item item = new Item();
        item.setId(cursor.getLong(cursor.getColumnIndexOrThrow(COLUMN_ID)));
        item.setTitle(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TITLE)));
        item.setDescription(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DESCRIPTION)));
        item.setCategory(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_CATEGORY)));
        item.setType(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TYPE)));
        item.setStatus(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_STATUS)));
        item.setDate(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DATE)));
        item.setLocation(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_LOCATION)));
        item.setContactName(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_CONTACT_NAME)));
        item.setContactPhone(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_CONTACT_PHONE)));
        item.setContactEmail(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_CONTACT_EMAIL)));
        item.setImageUri(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_IMAGE_URI)));
        item.setPostedBy(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_POSTED_BY)));
        return item;
    }
}