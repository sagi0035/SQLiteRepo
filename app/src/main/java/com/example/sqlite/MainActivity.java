package com.example.sqlite;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private Button buttonOne, buttonTwo;
    private EditText name, age;
    private Switch swActiveCustomer;
    private ListView lvCustomerList;
    private ArrayAdapter customerArrayAdapter;
    DataBaseHelper dataBaseHelper;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // so here we will set everything
        buttonOne = (Button) findViewById(R.id.btn_add);
        buttonTwo = (Button) findViewById(R.id.btn_view_all);
        name = (EditText) findViewById(R.id.etName);
        age = (EditText) findViewById(R.id.etNumber);
        swActiveCustomer = (Switch) findViewById(R.id.sw_active);
        lvCustomerList = (ListView) findViewById(R.id.custList);

        dataBaseHelper = new DataBaseHelper(MainActivity.this);

        customerArrayAdapter = new ArrayAdapter<CustomerModel>(MainActivity.this,android.R.layout.simple_list_item_1,dataBaseHelper.getEverything());
        lvCustomerList.setAdapter(customerArrayAdapter);



        buttonOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // and here we will set the Customermodel as per our initial values
                CustomerModel customerModel = new CustomerModel(-1,name.getText().toString(),
                        Integer.parseInt(age.getText().toString()),
                        swActiveCustomer.isChecked());
                Toast.makeText(MainActivity.this, customerModel.toString(), Toast.LENGTH_SHORT).show();

                // so now we will start creating the database
                DataBaseHelper dataBaseHelper = new DataBaseHelper(MainActivity.this);
                Boolean writeIt = dataBaseHelper.addOne(customerModel);
                Toast.makeText(MainActivity.this, writeIt.toString(), Toast.LENGTH_SHORT).show();

                customerArrayAdapter = new ArrayAdapter<CustomerModel>(MainActivity.this,android.R.layout.simple_list_item_1,dataBaseHelper.getEverything());
                lvCustomerList.setAdapter(customerArrayAdapter);
            }


        });

        // so we create a long click listener for the deleting
        lvCustomerList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(MainActivity.this, "This occurred!!!", Toast.LENGTH_SHORT).show();
                // so first things first we will get the customer that was clicked
                CustomerModel customerModel = (CustomerModel) parent.getItemAtPosition(position);
                // so now all that remains is to call the method for the deletion
                dataBaseHelper.deleteOne(customerModel);
                customerArrayAdapter = new ArrayAdapter<CustomerModel>(MainActivity.this,android.R.layout.simple_list_item_1,dataBaseHelper.getEverything());
                lvCustomerList.setAdapter(customerArrayAdapter);
                return false;
            }
        });

        buttonTwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // now here we will create the option to view everything in the sqlite database as per the values entered

                // first we will of course create a reference to the DataBaseHelper
                DataBaseHelper dataBaseHelper = new DataBaseHelper(MainActivity.this);
                List<CustomerModel> getTheList = dataBaseHelper.getEverything();

                // now we will add it other list view to do this we will first need our array adapter
                //customerArrayAdapter = new ArrayAdapter<CustomerModel>(MainActivity.this,android.R.layout.simple_list_item_1,getTheList);
                //lvCustomerList.setAdapter(customerArrayAdapter);
            }
        });

    }
}