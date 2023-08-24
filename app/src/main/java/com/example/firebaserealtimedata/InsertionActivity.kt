package com.example.firebaserealtimedata

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import java.io.FileDescriptor.err

private lateinit var etEmpName: EditText
private lateinit var etEmpAge: EditText
private lateinit var etEmpSalary: EditText
private lateinit var btnSaveData: Button

private lateinit var dbRef: DatabaseReference

class InsertionActivity : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_insertion)

        etEmpName=findViewById(R.id.etEmpName)
        etEmpAge=findViewById(R.id.etEmpAge)
        etEmpSalary=findViewById(R.id.etEmpSalary)
        btnSaveData=findViewById(R.id.btnSave)

        dbRef=FirebaseDatabase.getInstance().getReference("Employees")
        btnSaveData.setOnClickListener {
            saveEmployeeData()
        }
    }
    private fun saveEmployeeData(){
        val empName= etEmpName.text.toString()
        val empAge= etEmpAge.text.toString()
        val empSalary= etEmpSalary.text.toString()

        if(empName.isEmpty()){
            etEmpName.error="Please enter name"
        }
        if(empAge.isEmpty()){
            etEmpAge.error="Please enter age"
        }
        if(empSalary.isEmpty()){
            etEmpSalary.error="Please enter salary"
        }
        val empId= dbRef.push().key!!
        val employee=EmployeeModel(empId,empName,empAge,empSalary)
        dbRef.child(empId).setValue(employee).addOnCompleteListener{
            Toast.makeText(this,"Data Inserted successfully",Toast.LENGTH_LONG).show()
            etEmpName.text.clear()
            etEmpSalary.text.clear()
            etEmpAge.text.clear()
        }.addOnFailureListener {
            //Toast.makeText(this,"Error ${err.message}",Toast.LENGTH_LONG).show()
        }

    }
}