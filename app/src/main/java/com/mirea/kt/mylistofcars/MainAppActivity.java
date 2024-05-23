package com.mirea.kt.mylistofcars;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainAppActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText editTextBrand, editTextNumber, editTextYear;
    Button buttonSave;
    Button buttonContinue;



    private DBManager dbManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_app);
        editTextBrand = findViewById(R.id.editTextBrand);
        editTextNumber = findViewById(R.id.editTextNumber);
        editTextYear = findViewById(R.id.editTextYear);
        buttonSave = findViewById(R.id.buttonSave);
        buttonContinue = findViewById(R.id.buttonContinue);


        buttonSave.setOnClickListener(this);
        buttonContinue.setOnClickListener(this);

        this.dbManager = new DBManager(new MyAppSQLiteHelper(this,"my_database.db",null,1));



    }

    @Override
    public void onClick(View v) {

        if (v.getId() == R.id.buttonSave){
            String brand = editTextBrand.getText().toString();
            String number = editTextNumber.getText().toString();
            String year = editTextYear.getText().toString() ;
            String pattern = "[A-Za-z]\\d{3}[A-Za-z]{2}";
            Pattern p = Pattern.compile(pattern);
            Matcher m = p.matcher(number);
            if(!brand.isEmpty() && !number.isEmpty() && !year.isEmpty() && m.matches() && 1920<Integer.parseInt(year) && Integer.parseInt(year)<2025){
                boolean result = dbManager.saveCarToDatabase(new Car(brand,number,Integer.parseInt(year)));
                if(result){
                    Toast.makeText(this, R.string.insert_success, Toast.LENGTH_LONG).show();
                }else{
                    Toast.makeText(this, R.string.insert_error, Toast.LENGTH_LONG).show();
                }
            }else{
                Toast.makeText(this, R.string.incorrect_value, Toast.LENGTH_LONG).show();
            }
        } else if (v.getId() == R.id.buttonContinue) {
            startActivity(new Intent(this, ShowCarsActivity.class));

        }
    }
}