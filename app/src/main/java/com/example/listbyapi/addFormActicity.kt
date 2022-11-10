package com.example.listbyapi

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class addFormActicity : AppCompatActivity() {
    lateinit var intitule : EditText
    lateinit var specialite : EditText
    lateinit var societe : EditText
    lateinit var pays : EditText
    lateinit var nbpostes : EditText
    lateinit var btn : Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_form_acticity)
        intitule = findViewById(R.id.intitule)
        specialite = findViewById(R.id.specialité)
        societe = findViewById(R.id.société)
        pays = findViewById(R.id.pays)
        nbpostes = findViewById(R.id.nbpostes)
        btn = findViewById(R.id.submit)
        val intent = intent
        val id = intent.getIntExtra("id",0)
        val intituleV = intent.getStringExtra("intitule")
        val specialitéV = intent.getStringExtra("specialité")
        val sociétéV = intent.getStringExtra("société")
        val paysV = intent.getStringExtra("pays")
        val nbpostesV = intent.getIntExtra("nbpostes",0)
        if (id!=0){
            intitule.setText(intituleV)
            specialite.setText(specialitéV)
            societe.setText(sociétéV)
            pays.setText(paysV)
            nbpostes.setText(nbpostesV.toString())
        }
        btn.setOnClickListener{
            val offre = Offre(
                intitulé = intitule.text.toString(),
                specialité = specialite.text.toString(),
                société = societe.text.toString(),
                nbpostes = nbpostes.text.toString().toInt(),
                pays = pays.text.toString()
            )
            val scope = CoroutineScope(Dispatchers.Main)
            scope.launch {
                try{
                    if (id!=0){
                        Apiclient.apiService.updateOffer(id,offre)
                    }else {
                        Apiclient.apiService.addOffer(offre)
                    }
                    finish()
                } catch (e: Exception) {
                    Log.e("Error",e.message.toString())
                }
            }
        }

    }
}