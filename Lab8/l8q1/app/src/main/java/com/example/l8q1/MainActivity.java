package com.example.l8q1;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AlertDialog;

public class MainActivity extends AppCompatActivity {
    private EditText etProduct, etPrice, etQuantity;
    private DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbHelper = new DBHelper(this);
        etProduct = findViewById(R.id.etProduct);
        etPrice = findViewById(R.id.etPrice);
        etQuantity = findViewById(R.id.etQuantity);
        ListView listView = findViewById(R.id.listView);

        findViewById(R.id.btnAdd).setOnClickListener(v -> addBillItem());
        findViewById(R.id.btnStats).setOnClickListener(v -> showStatistics());

        refreshBillList(listView);
    }

    private void addBillItem() {
        String product = etProduct.getText().toString();
        String priceStr = etPrice.getText().toString();
        String quantityStr = etQuantity.getText().toString();

        if(product.isEmpty() || priceStr.isEmpty() || quantityStr.isEmpty()) {
            Toast.makeText(this, "Fill all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        double price = Double.parseDouble(priceStr);
        int quantity = Integer.parseInt(quantityStr);

        boolean added = dbHelper.addBillItem(product, price, quantity);
        if(added) {
            Toast.makeText(this, "Added to bill", Toast.LENGTH_SHORT).show();
            refreshBillList((ListView) findViewById(R.id.listView));
            clearFields();
        } else {
            Toast.makeText(this, "Failed to add", Toast.LENGTH_SHORT).show();
        }
    }

    private void refreshBillList(ListView listView) {
        Cursor cursor = dbHelper.getAllBills();
        SimpleCursorAdapter adapter = new SimpleCursorAdapter(
                this,
                R.layout.bill_item,
                cursor,
                new String[]{"_id", DBHelper.COL_PRODUCT, DBHelper.COL_QUANTITY, DBHelper.COL_PRICE},
                new int[]{R.id.tvId, R.id.tvProduct, R.id.tvQty, R.id.tvPrice},
                0
        );
        listView.setAdapter(adapter);
    }



    // Add new method to show statistics
    private void showStatistics() {
        int total = dbHelper.getTotalQuantity();
        String max = dbHelper.getMaxProduct();
        String min = dbHelper.getMinProduct();

        String message = "Total Products Billed: " + total + "\n\n" +
                "Highest Priced Item: " + max + "\n" +
                "Lowest Priced Item: " + min;

        new AlertDialog.Builder(this)
                .setTitle("Sales Statistics")
                .setMessage(message)
                .setPositiveButton("OK", null)
                .show();
    }

    private void clearFields() {
        etProduct.setText("");
        etPrice.setText("");
        etQuantity.setText("");
    }

    @Override
    protected void onDestroy() {
        dbHelper.close();
        super.onDestroy();
    }
}