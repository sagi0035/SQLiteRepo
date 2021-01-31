package com.example.sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

// so we will have the class inherit the SQLiteOpenHelper
public class DataBaseHelper extends SQLiteOpenHelper {
    public static final String CUSTOMER_TABLE = "CUSTOMER_TABLE";
    public static final String COLUMN_CUSTOMER_NAME = "CUSTOMER_NAME";
    public static final String COLUMN_CUSTOMER_AGE = "CUSTOMER_AGE";
    public static final String COLUMN_ACTIVE_CUSTOMER = "ACTIVE_CUSTOMER";
    public static final String COLUMN_ID = "ID";

    /*
    public DataBaseHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

     */

    public DataBaseHelper(@Nullable Context context) {
        // so note that this can be set with the parameters or manually
        super(context, "customers.db", null, 1);
    }

    // so obviously this is whenever the database is first accessed!
    // so we will go about creating the database
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        //  the below statement must be implemented to work with sqlite only CUSTOMER_TABLE is specific
        String createTableStatement = "CREATE TABLE " + CUSTOMER_TABLE + "(ID INTEGER PRIMARY KEY AUTOINCREMENT, " + COLUMN_CUSTOMER_NAME + " TEXT," + COLUMN_CUSTOMER_AGE + " INT," + COLUMN_ACTIVE_CUSTOMER + " BOOL)";
        // always execute the sql
        sqLiteDatabase.execSQL(createTableStatement);
    }

    // this is obviously for the sake of amendments when he version of your database is changed
    // (it prevents it from crashing all together!)
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    // so these methods are now all about adding to the database
    public boolean addOne(CustomerModel customerModel) {
        SQLiteDatabase db = this.getWritableDatabase();
        // so this all about creating key/value pairs were cv.put("add",value) cv.getString("add")
        ContentValues cv = new ContentValues();
        // so we fill in as per the static's of above
        cv.put(COLUMN_CUSTOMER_NAME,customerModel.getName());
        cv.put(COLUMN_CUSTOMER_AGE,customerModel.getAge());
        cv.put(COLUMN_ACTIVE_CUSTOMER,customerModel.getActive());


        // note that the 2nd parameter is a default if you are not putting a column name
        // since we do in fact have a name this time we can just set it to null
        long insert = db.insert(CUSTOMER_TABLE, null, cv);
        // note that the long will provide a positive value if success and negative if fail

        if (insert == -1) {
            return false;
        } else {
            return true;
        }

    }

    // so now this will allow us to delete a specific data
    public boolean deleteOne(CustomerModel customerModel) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        String queryString = "DELETE FROM " + CUSTOMER_TABLE + " WHERE " + COLUMN_ID + " = " + customerModel.getId();
        Cursor cursor = sqLiteDatabase.rawQuery(queryString,null);

        if (cursor.moveToFirst()) {
            return true;
        } else {
            return false;
        }

    }


    // and now we will go about creating a method for obtaining everything from the method
    public List<CustomerModel> getEverything() {

        List<CustomerModel> returnList = new ArrayList<>();

        // so this is how we go about selecting specific data from the database
        String queryString = "SELECT * FROM " + CUSTOMER_TABLE;
        // now note that we will actually not be writing to the datbase we only get the readable
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();

        // so now we will do this because it returns a cursor
        // a cursor is the result set from the query
        Cursor cursor = sqLiteDatabase.rawQuery(queryString,null);

        // so now this is how to go about checking if there were in factr resultrs
        if (cursor.moveToFirst()) {
            // so if there was in fact a first to move to it was a success

            //now we will lopp though it until we reach the end
            do {
                // so now we get each data value as per their value in the table
                int customerId = cursor.getInt(0);
                String customerName = cursor.getString(1);
                int age = cursor.getInt(2);
                // so now what of the boolean?
                // well booleans do not work on sqlit
                // but this should not be a problem because they ae saved as 1's and 0's instead!
                // we do this by way of a ternary operator for returning a true or false value
                boolean customerActive = cursor.getInt(3) == 1 ? true : false;

                // so now we will create the Customer Miodel as per the vars that we defined
                CustomerModel newCustomer = new CustomerModel(customerId,customerName,age,customerActive);
                // and we will add it to the returnlist
                returnList.add(newCustomer);

            } while (cursor.moveToNext());

            }

            // always remember to close everything within sqlite
            cursor.close();
            sqLiteDatabase.close();

            return returnList;
        }


    }


