package com.jackson.android

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat.startActivity
import androidx.core.content.pm.ShortcutInfoCompat
import androidx.core.content.pm.ShortcutManagerCompat
import androidx.core.graphics.drawable.IconCompat
import co.notix.interstitial.NotixInterstitial
import com.google.firebase.Firebase
import com.google.firebase.FirebaseApp
import com.google.firebase.FirebaseOptions
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.database
import com.jackson.framework.feature.convertor.ui.ConvertorScreen

class ConvertorActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ConvertorScreen(this@ConvertorActivity)

            Button(modifier = Modifier.padding(54.dp),
                onClick = { AdGalaxyApplication.INSTANCE.interstitialLoader?.doOnNextAvailable {
                    if(it != null){
                        NotixInterstitial.show(it)
                    }
                }
                }) {
                Text(text = "CLICK IT")
            }

        }

        /*updateAdUnitData(this, getString(R.string.firebase_app_id),
            getString(R.string.firebase_project_id),
            getString(R.string.firebase_database_url), getString(R.string.app_name)){
            *//*ConvertorCore.updateAvengerAdData(this, AvengerAdData(it),
                AdGalaxyApplication.INSTANCE.globalDefaultScope)*//*
        }*/
    }


}



fun updateAdUnitData(context : Context, appId:String, projectId:String, databaseUrl:String,
                     appName:String, updateAdUnitList:(List<String>) -> Unit){
    val databaseColumn = "applovin_ad_unit_list"
    val firebaseOptions = FirebaseOptions.Builder()
        .setApplicationId(appId)
        .setProjectId(projectId)
        .setDatabaseUrl(databaseUrl).build()

    val firebaseApp = FirebaseApp.initializeApp(context, firebaseOptions, appName)
    val database = Firebase.database.reference


    database.addValueEventListener(object : ValueEventListener{
        override fun onDataChange(snapshot : DataSnapshot) {
            val adUnitList = arrayListOf<String>()
            val children = snapshot.child(databaseColumn).children
            for(data in children){
                val value = data.value
                if(value is String){
                    adUnitList.add(value)
                }
            }

            updateAdUnitList(adUnitList)
            // ConvertorCore.updateAvengerAdData(AvengerAdData(adUnitList))
        }

        override fun onCancelled(error : DatabaseError) {
            updateAdUnitList(listOf())
        }

    })
}