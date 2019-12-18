package com.example.mystuff7;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import java.util.ArrayList;

public class DBHelper extends SQLiteOpenHelper {

    private String table_Name = "Produkty";
    private String key_ID = "ID";
    private String key_Nazwa = "Nazwa";
    private String key_Liczba = "Liczba";
    private String key_Cena = "Cena";
    private SQLiteDatabase db;
    private Context conn;

    public DBHelper(Context context, String name, int version) {
        super(context, name, null, version);
        this.conn = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Tworzenie bazy danych
        String sql = "CREATE TABLE " + table_Name + " ( " +
                key_ID + " INTEGER PRIMARY KEY, " +
                key_Nazwa + " TEXT  UNIQUE," +
                key_Liczba + " INTEGER," +
                key_Cena  +" REAL);";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Toast.makeText(conn, "Aktualizacja bazy danych", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Toast.makeText(conn, "Zmiany w bazach danych", Toast.LENGTH_SHORT).show();
    }

    public long insertProduct(String nazwa, String liczba, String cena){
        
        ContentValues content = new ContentValues();
        content.put(key_Nazwa, nazwa);
        content.put(key_Liczba, liczba);
        content.put(key_Cena, cena);
        
        db = this.getWritableDatabase();
        
        long index = db.insert(table_Name, null, content);
        
        db.close();
        return index;
    }

    public ArrayList<String> getAllProducts(){

        ArrayList<String> tempList = new ArrayList<>();
        
        db = this.getReadableDatabase();
        
        Cursor cursor = db.query(table_Name, new String[] {key_ID, key_Nazwa, key_Liczba, key_Cena},
                null, null, null, null, null);


        while(cursor.moveToNext()){
            tempList.add(cursor.getString(0) + ") " +
                    cursor.getString(cursor.getColumnIndex(key_Nazwa)) + " " +
                    cursor.getString(cursor.getColumnIndex(key_Liczba)) + ", " +
                    cursor.getString(cursor.getColumnIndex(key_Cena)) + ", " +
                    cursor.getString(3));
        }
        
        db.close();
        
        cursor.close();
        return tempList;
    }

    public long updateProducts(){
        db = this.getWritableDatabase();
        // przygotowanie danych do aktualizacji
        ContentValues content = new ContentValues();
        content.put(key_Liczba, 20);
        // aktualizowanie wybranych rekordów z tabeli oraz zwrucenie liczby zaktualizowanych rekordów
        long nr = db.update(table_Name,content, key_Liczba + " < ?", new String[] {"3"});//?
        // zamknięcie połączenia z bazą danych
        db.close();
        return nr;
    }

    public long deleteProduct(String nazwa){
        
        db = this.getWritableDatabase();
        
        long nr = db.delete(table_Name, key_Nazwa + " like ? ", new String[] {nazwa});
        
        db.close();
        return nr;
    }
}
