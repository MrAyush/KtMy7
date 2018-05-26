package com.example.ayushgupta.ktmy7

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.ListView
import android.widget.Toast

class MainActivity : AppCompatActivity() {

    private var et1:EditText? = null
    private var et2:EditText? = null
    private var et3:EditText? = null
    private var et4:EditText? = null
    private var dBase:SQLiteDatabase? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        et1 = findViewById(R.id.et1)
        et2 = findViewById(R.id.et2)
        et3 = findViewById(R.id.et3)
        et4 = findViewById(R.id.et4)
        dBase = openOrCreateDatabase("empdb", Context.MODE_PRIVATE,null)
        dBase?.execSQL("create table if not exists employee(id number primary key, name varchar(100), desig varchar(100), salary number)")

    }
    fun clickEvent(v: View){
        when(v.id){
            R.id.insert->{
                val c = ContentValues()
                c.put("id",et1?.text.toString().toInt())
                c.put("name",et2?.text.toString())
                c.put("desig",et3?.text.toString())
                c.put("salary",et4?.text.toString().toLong())
                val status = dBase?.insert("employee",null,c)
                if (status == (-1).toLong()){
                    Toast.makeText(this,"Error has occurred can't insert",Toast.LENGTH_SHORT).show()
                }else{
                    Toast.makeText(this,"Record has been inserted",Toast.LENGTH_SHORT).show()
                }
                et1?.setText("")
                et2?.setText("")
                et3?.setText("")
                et4?.setText("")
            }
            R.id.read->{
                val list:ArrayList<String>? = ArrayList()
                val c = dBase?.query("employee",null,null,null,null,null,null)
                while (c!!.moveToNext()){
                    list!!.add(c.getString(0) + "|" + c.getString(1) + "|" + c.getString(2)+ "|" + c.getString(3) )
                }
                val adapter:ArrayAdapter<String> = ArrayAdapter(this,android.R.layout.simple_list_item_1,list)
                val lview:ListView = findViewById(R.id.lview)
                lview.adapter = adapter
            }
            R.id.update->{
                val c = ContentValues()
                c.put("name",et2?.text.toString())
                c.put("desig",et3?.text.toString())
                c.put("salary",et4?.text.toString().toLong())
                val status = dBase!!.update("employee",c,"id=?", arrayOf(et1?.text.toString()))
                if (status == 0){
                    Toast.makeText(this,"Error has occurred can't update",Toast.LENGTH_SHORT).show()
                }else{
                    Toast.makeText(this,"Record has been updated",Toast.LENGTH_SHORT).show()
                }
                et1?.setText("")
                et2?.setText("")
                et3?.setText("")
                et4?.setText("")
            }
            R.id.del->{
                val status = dBase!!.delete("employee","id=?", arrayOf(et1?.text.toString()))
                if (status == 0){
                    Toast.makeText(this,"Error has occurred can't delete",Toast.LENGTH_SHORT).show()
                }else{
                    Toast.makeText(this,"Record has been deleted",Toast.LENGTH_SHORT).show()
                }
                et1?.setText("")
                et2?.setText("")
                et3?.setText("")
                et4?.setText("")
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        dBase?.close()
    }
}
