package com.example.listbyapi

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    lateinit var recycler : RecyclerView
    private lateinit var adapter: CustomAdapter
    lateinit var offerList: MutableList<Offre>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        recycler = findViewById(R.id.my_recycler_view)
        recycler.layoutManager = androidx.recyclerview.widget.LinearLayoutManager(this)
        offerList=ArrayList()
        adapter = CustomAdapter(offerList)
        recycler.adapter = adapter
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
    }
}