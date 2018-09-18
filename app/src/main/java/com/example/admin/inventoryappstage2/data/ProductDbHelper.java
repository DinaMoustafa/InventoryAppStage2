package com.example.admin.inventoryappstage2.data;
import com.example.admin.inventoryappstage2.data.ProductContract.ProductEntry;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
public class ProductDbHelper extends SQLiteOpenHelper {
    private final static String DATABASE_NAME = "product.db";
    private final static int DATABASE_VERSION = 1;
    public ProductDbHelper(Context c) {
        super(c, DATABASE_NAME, null, DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        String SQL_CREATE_PRODUCT_TABLE =
                "CREATE TABLE " + ProductEntry.TABLE_NAME + "( " +
                        ProductEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        ProductEntry.COLUMN_PRODUCT_NAME + " TEXT NOT NULL, " +
                        ProductEntry.COLUMN_PRICE + " INTEGER NOT NULL, " +
                        ProductEntry.COLUMN_QUANTITY + " INTEGER NOT NULL DEFAULT 0, " +
                        ProductEntry.COLUMN_SUPPLIER_NAME + " TEXT, " +
                        ProductEntry.COLUMN_SUPPLIER_PHONE_NUMBER + " TEXT );";
        db.execSQL(SQL_CREATE_PRODUCT_TABLE);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // The database is still at version 1, so there's nothing to do be done here.
    }
}
