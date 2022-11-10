package com.example.listbyapi

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    lateinit var recycler : RecyclerView
    private lateinit var adapter: CustomAdapter
    lateinit var offerList: MutableList<Offre>
    lateinit var delete : FloatingActionButton
    lateinit var add : FloatingActionButton
    lateinit var update : FloatingActionButton
    var selectedItem : Offre? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        var delete = findViewById<FloatingActionButton>(R.id.delete)
        var update = findViewById<FloatingActionButton>(R.id.update)
        var add = findViewById<FloatingActionButton>(R.id.add)
        recycler = findViewById(R.id.my_recycler_view)
        recycler.layoutManager = androidx.recyclerview.widget.LinearLayoutManager(this)
        offerList=ArrayList()
        adapter = CustomAdapter(offerList)
        recycler.adapter = adapter
        adapter.onItemClick = {
            selectedItem = it
            delete.isEnabled = true
            update.isEnabled = true
        }
        val scope = CoroutineScope(Dispatchers.Main)
        scope.launch {
            try{
                val response = Apiclient.apiService.getOffers()
                if (response.isSuccessful && response.body() != null) {
                    Log.i("Success",response.body().toString())
                    offerList.addAll(response.body()!!)
                    adapter.notifyDataSetChanged()

                }else{
                    Log.e("Error",response.message())
                }
            } catch (e: Exception) {
                Log.e("Error",e.message.toString())
            }
        }
        if (selectedItem==null){
            delete.isEnabled=false
            update.isEnabled=false
        }
        delete.setOnClickListener{
            if (selectedItem!=null){
                    val scope = CoroutineScope(Dispatchers.Main)
                    scope.launch {
                        try{
                            val response = Apiclient.apiService.deleteOffer(selectedItem!!.id!!)
                            offerList.removeAt(offerList.indexOf(selectedItem!!))
                            adapter.notifyDataSetChanged()
                            selectedItem = null
                        } catch (e: Exception) {
                            Log.e("Error",e.message.toString())
                        }
                    }

                }
            }
        var resultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            val scope = CoroutineScope(Dispatchers.Main)
            scope.launch {
                try{
                    val response = Apiclient.apiService.getOffers()
                    if (response.isSuccessful && response.body() != null) {
                        Log.i("Success",response.body().toString())
                        offerList.clear()
                        offerList.addAll(response.body()!!)
                        adapter.notifyDataSetChanged()

                    }else{
                        Log.e("Error",response.message())
                    }
                } catch (e: Exception) {
                    Log.e("Error",e.message.toString())
                }
            }
        }
        add.setOnClickListener{
            intent = android.content.Intent(this,addFormActicity::class.java)
            resultLauncher.launch(intent)
        }
        update.setOnClickListener{
            intent = android.content.Intent(this,addFormActicity::class.java)
            intent.putExtra("id",selectedItem!!.id)
            intent.putExtra("intitule",selectedItem!!.intitulé)
            intent.putExtra("specialité",selectedItem!!.specialité)
            intent.putExtra("société",selectedItem!!.société)
            intent.putExtra("nbpostes",selectedItem!!.nbpostes)
            intent.putExtra("pays",selectedItem!!.pays)
            resultLauncher.launch(intent)
        }



    }
}