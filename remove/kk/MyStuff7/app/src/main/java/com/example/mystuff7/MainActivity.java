package com.example.mystuff7;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

//Author: Zbigniew Smaza
//Grupa1: zadanie 3
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final EditText etNazwa = (EditText)findViewById(R.id.eTName);
        final EditText etLiczba = (EditText)findViewById(R.id.eTLiczba);
        final EditText etCena = (EditText)findViewById(R.id.eTCena);

        Button bSave = (Button)findViewById(R.id.bttnAdd);
        Button bUpdate = (Button)findViewById(R.id.bttnUpDate);
        Button bDelete = (Button)findViewById(R.id.bttnRemove);

        final ListView lvPerson = (ListView)findViewById(R.id.Lista);

        final DBHelper helper = new DBHelper(this, "Produkty.db", 1);

        final ArrayList<String> productList = helper.getAllProducts();

        final ArrayAdapter arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_expandable_list_item_1, productList);
        lvPerson.setAdapter(arrayAdapter);

        bSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = etNazwa.getText().toString();
                String lastname = etLiczba.getText().toString();
                String city = etCena.getText().toString();
                long id = helper.insertProduct(name, lastname, city);
                if (id != -1){
                    productList.clear();
                    productList.addAll(helper.getAllProducts());
                    arrayAdapter.notifyDataSetChanged();
                    Toast.makeText(MainActivity.this, "Id " + id, Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(MainActivity.this, "Coś poszło nie tak", Toast.LENGTH_SHORT).show();
                }
            }
        });

        bUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                long update = helper.updateProducts();
                if (update != -1){
                    productList.clear();
                    productList.addAll(helper.getAllProducts());
                    arrayAdapter.notifyDataSetChanged();
                    Toast.makeText(MainActivity.this, "Aktualizacja rekordu " + update, Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(MainActivity.this, "Coś poszło nie tak", Toast.LENGTH_SHORT).show();
                }
            }
        });

        bDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nazwa = etNazwa.getText().toString();
                long delete = helper.deleteProduct(nazwa);
                if (delete != -1){
                    productList.clear();
                    productList.addAll(helper.getAllProducts());
                    arrayAdapter.notifyDataSetChanged();
                    Toast.makeText(MainActivity.this, "Usunięto rekordu " + delete, Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(MainActivity.this, "Coś poszło nie tak", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }
}