package com.example.admin.inventoryappstage2;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.support.design.widget.FloatingActionButton;
import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import com.example.admin.inventoryappstage2.data.ProductContract.ProductEntry;
public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {
    /**
     * Identifier for the product data loader
     */
    private static final int Product_LOADER = 0;
    /**
     * Adapter for the ListView
     */
    ProductCursorAdapter mProductCursorAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Setup FAB to open EditorActivity
        FloatingActionButton mFloatingActionBar = (FloatingActionButton) findViewById(R.id.fab);
        mFloatingActionBar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, EditorActivity.class);
                startActivity(intent);
            }
        });
        // Find the ListView which will be populated with the Product data
        ListView productListView = (ListView) findViewById(R.id.list);
        // Find and set empty view on the ListView, so that it only shows when the list has 0 items.
        View emptyView = findViewById(R.id.empty_view);
        productListView.setEmptyView(emptyView);
        // Setup an Adapter to create a list item for each row of product data in the Cursor.
        // There is no product data yet (until the loader finishes) so pass in null for the Cursor.
        mProductCursorAdapter = new ProductCursorAdapter(this, null);
        productListView.setAdapter(mProductCursorAdapter);
        getLoaderManager().initLoader(Product_LOADER, null, this);
    }
    /**
     * Helper method to insert hardcoded product data into the database. For debugging purposes only.
     */
    private void insertDummyProduct() {
        ContentValues values = new ContentValues();
        values.put(ProductEntry.COLUMN_PRODUCT_NAME, "Pepsi");
        values.put(ProductEntry.COLUMN_PRICE, 5);
        values.put(ProductEntry.COLUMN_QUANTITY, 10);
        values.put(ProductEntry.COLUMN_SUPPLIER_NAME, "Pepsi Company");
        values.put(ProductEntry.COLUMN_SUPPLIER_PHONE_NUMBER, "01234567890");
        getContentResolver().insert(ProductEntry.CONTENT_URI, values);
    }
    /**
     * Helper method to delete all products in the database.
     */
    private void deleteAllProducts() {
        int rowsDeleted = getContentResolver().delete(ProductEntry.CONTENT_URI, null, null);
        Log.v("MainActivity", rowsDeleted + " rows deleted from product database");
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu options from the res/menu/menu_catalog.xml file.
        // This adds menu items to the app bar.
        getMenuInflater().inflate(R.menu.menu_catalog, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
// User clicked on a menu option in the app bar overflow menu
        switch (item.getItemId()) {
            case R.id.insert_dummy_data:
                insertDummyProduct();
                return true;
            case R.id.delete_all_entries:
                deleteAllProducts();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        // Define a projection that specifies the columns from the table we care about.
        String[] projection =
                {ProductEntry._ID,
                        ProductEntry.COLUMN_PRODUCT_NAME,
                        ProductEntry.COLUMN_PRICE,
                        ProductEntry.COLUMN_QUANTITY};
        // This loader will execute the ContentProvider's query method on a background thread
        return new CursorLoader(this,
                ProductEntry.CONTENT_URI,
                projection,
                null,
                null,
                null);
    }
    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        // Update {@link ProductCursorAdapter} with this new cursor containing updated product data
        mProductCursorAdapter.swapCursor(cursor);
    }
    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        // Callback called when the data needs to be deleted
        mProductCursorAdapter.swapCursor(null);
    }
}
