package com.example.l2q1;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private ListView listViewGrocery;
    private final String[] groceryItems = {
            "Apples",
            "Bananas",
            "Milk",
            "Bread",
            "Eggs",
            "Cheese",
            "Chicken",
            "Rice",
            "Pasta",
            "Tomatoes"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listViewGrocery = findViewById(R.id.listViewGrocery);

        // Create adapter for list view
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_1,
                groceryItems
        );

        listViewGrocery.setAdapter(adapter);

        // Handle item selection
        listViewGrocery.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String selectedItem = groceryItems[position];
                String message = "Selected: " + selectedItem;

                Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT).show();
            }
        });
    }
}