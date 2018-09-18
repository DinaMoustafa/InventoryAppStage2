package com.example.admin.inventoryappstage2;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.example.admin.inventoryappstage2.data.ProductContract.ProductEntry;
public class ProductCursorAdapter extends CursorAdapter {
    public ProductCursorAdapter(Context context, Cursor c) {
        super(context, c, 0);
    }
    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        // Inflate a list item view using the layout specified in list_item.xml
        return LayoutInflater.from(context).inflate(R.layout.list_item, parent, false);
    }
    @Override
    public void bindView(View view, final Context context, Cursor cursor) {
        // Find individual views that we want to modify in the list item layout
        TextView name = view.findViewById(R.id.product_name);
        TextView price = view.findViewById(R.id.product_price);
        final TextView quantityTextView = view.findViewById(R.id.product_quantity);
        RelativeLayout parentView = (RelativeLayout) view.findViewById(R.id.parent_layout);
// Find the columns of pet attributes that we're interested in
        int idColumnIndex = cursor.getColumnIndex(ProductEntry._ID);
        int nameColumnIndex = cursor.getColumnIndex(ProductEntry.COLUMN_PRODUCT_NAME);
        int priceColumnIndex = cursor.getColumnIndex(ProductEntry.COLUMN_PRICE);
        int quantityColumnIndex = cursor.getColumnIndex(ProductEntry.COLUMN_QUANTITY);
        // Read the product attributes from the Cursor for the current product
        final int rowId = cursor.getInt(idColumnIndex);
        String productName = cursor.getString(nameColumnIndex);
        double productPrice = cursor.getDouble(priceColumnIndex);
        final int productQuantity = cursor.getInt(quantityColumnIndex);
        // Update the TextViews with the attributes for the current product
        quantityTextView.setText(String.valueOf(productQuantity));
        name.setText(productName);
        price.setText(String.valueOf(productPrice));
        parentView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Open EditorActivity
                Intent intent = new Intent(context, EditorActivity.class);
                // Form the content URI that represents clicked product.
                Uri currentInventoryUri = ContentUris.withAppendedId(ProductEntry.CONTENT_URI, rowId);
                // Set the URI on the data field of the intent
                intent.setData(currentInventoryUri);
                context.startActivity(intent);
            }
        });
        Button saleButton = view.findViewById(R.id.product_sale);
        saleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text = quantityTextView.getText().toString();
                String[] splittedText = text.split(" ");
                int quantity = Integer.parseInt(splittedText[0]);
                if (quantity == 0) {
                    Toast.makeText(context, "No more products", Toast.LENGTH_SHORT).show();
                } else if (quantity > 0) {
                    quantity = quantity - 1;
                    String quantityString = Integer.toString(quantity);
                    ContentValues values = new ContentValues();
                    values.put(ProductEntry.COLUMN_QUANTITY, quantityString);
                    Uri currentInventoryUri = ContentUris.withAppendedId(ProductEntry.CONTENT_URI, rowId);
                    context.getContentResolver().update(currentInventoryUri, values, null, null);
                }
            }
        });
    }
}
