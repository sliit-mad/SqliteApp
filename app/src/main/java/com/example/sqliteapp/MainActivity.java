package com.example.sqliteapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    DatabaseHelper myDb;
    EditText editName,editSurname,editMarks,editTextId;
    Button btnAddData,btnviewAll,btnUpdate,btnDelete;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myDb = new DatabaseHelper(this);

        editName=(EditText)findViewById(R.id.editText_name);
        editSurname=(EditText)findViewById(R.id.editText2_sname);
        editMarks=(EditText)findViewById(R.id.editText3_marks);
        editTextId=(EditText)findViewById(R.id.editText4_id);
        btnAddData=(Button)findViewById(R.id.button_add);
        btnviewAll=(Button)findViewById(R.id.button2_view);
        btnUpdate=(Button)findViewById(R.id.button3_updata);
        btnDelete=(Button)findViewById(R.id.button_delete);
        AddData();
        viewAll();
        UpdateData();
        deleteData();
    }

    public void AddData(){
        btnAddData.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        boolean isInserted=myDb.insertData(editName.getText().toString(),
                                editSurname.getText().toString(),
                                editMarks.getText().toString());

                        if(isInserted==true)
                            Toast.makeText(MainActivity.this,"Data Inserted",Toast.LENGTH_LONG).show();
                        else
                            Toast.makeText(MainActivity.this,"Data not Inserted",Toast.LENGTH_LONG).show();
                    }
                }
        );
    }

    public void viewAll(){
        btnviewAll.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Cursor res=myDb.getAllData();
                        if(res.getCount() == 0){
                            //show message
                            showMessage("Error","Data not Found");
                            return;
                        }
                        StringBuffer buffer=new StringBuffer();
                        while (res.moveToNext()){
                            buffer.append("Id :"+ res.getString(0)+"\n");
                            buffer.append("Name :"+ res.getString(1)+"\n");
                            buffer.append("Surname :"+ res.getString(2)+"\n");
                            buffer.append("Marks :"+ res.getString(3)+"\n\n");

                        }
                        //show message
                        showMessage("Data",buffer.toString());
                    }
                }
        );
    }

    public void showMessage(String title,String Message){
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(Message);
        builder.show();
    }

    public void UpdateData(){
        btnUpdate.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        boolean isUpdate=myDb.updateData(editTextId.getText().toString(),
                                editName.getText().toString(),
                                editSurname.getText().toString(),
                                editMarks.getText().toString());
                        if(isUpdate==true)
                            Toast.makeText(MainActivity.this,"Data Updated",Toast.LENGTH_LONG).show();
                        else
                            Toast.makeText(MainActivity.this,"Data not Updated",Toast.LENGTH_LONG).show();
                    }
                }
        );
    }

    public void deleteData(){
        btnDelete.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Integer deleteRows=myDb.deleteData(editTextId.getText().toString());
                        if(deleteRows > 0)
                            Toast.makeText(MainActivity.this,"Data Deleted",Toast.LENGTH_LONG).show();
                        else
                            Toast.makeText(MainActivity.this,"Data not Deleted",Toast.LENGTH_LONG).show();
                    }
                }
        );
    }
}
