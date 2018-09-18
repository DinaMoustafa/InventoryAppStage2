package com.example.admin.inventoryappstage2.data;
import android.content.ContentResolver;
import android.net.Uri;
import android.provider.BaseColumns;
public final class ProductContract {
    private ProductContract() {
    }
    public static final String CONTENT_AUTHORITY = "com.example.admin.inventoryappstage2";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);
    public static final String PATH_PRODUCT = "products";
    public final static class ProductEntry implements BaseColumns {
        public static final Uri CONTENT_URI = Uri.withAppendedPath(BASE_CONTENT_URI, PATH_PRODUCT);
        /**
         * The MIME type of the {@link #CONTENT_URI} for a list of products.
         */
        public static final String CONTENT_LIST_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_PRODUCT;
        /**
         * The MIME type of the {@link #CONTENT_URI} for a single product.
         */
        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_PRODUCT;
        public final static String TABLE_NAME = "Products";
        public final static String _ID = BaseColumns._ID;
        public final static String COLUMN_PRODUCT_NAME = "ProductName";
        public final static String COLUMN_PRICE = "Price";
        public final static String COLUMN_QUANTITY = "Quantity";
        public final static String COLUMN_SUPPLIER_NAME = "SupplierName";
        public final static String COLUMN_SUPPLIER_PHONE_NUMBER = "SupplierPhone";
    }
}
