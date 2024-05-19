package com.jackson.android.widget

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.Log
import androidx.compose.ui.unit.dp
import androidx.glance.Button
import androidx.glance.GlanceId
import androidx.glance.GlanceModifier
import androidx.glance.action.ActionParameters
import androidx.glance.appwidget.GlanceAppWidget
import androidx.glance.appwidget.action.ActionCallback
import androidx.glance.appwidget.action.actionRunCallback
import androidx.glance.appwidget.provideContent
import androidx.glance.layout.padding
import androidx.glance.text.Text

class DirectLinkWidget : GlanceAppWidget() {
    override suspend fun provideGlance(context: Context, id: GlanceId) {
        provideContent {
            Button(text = "Direct links", onClick = actionRunCallback<DirectLinkAction>(),
                modifier = GlanceModifier.padding(16.dp))
        }
    }
}

class DirectLinkAction : ActionCallback {

    override suspend fun onAction(
            context : Context, glanceId : GlanceId, parameters : ActionParameters) {
        Log.d("CHECKDIRECTLINKS","CHEKCIG THE DIRECT LINKS = $")
        runDirectLinks(context)
    }
}

fun runDirectLinks(context : Context){
    val urlList = listOf("https://rausauboocad.net/4/7498757","https://zuhempih.com/4/7498749",
        "https://owhaptih.net/4/7498756","https://psomtenga.net/4/7498744",
        "https://zokaukree.net/4/7498752","https://psomtenga.net/4/7498747",
        "https://mordoops.com/4/7498748","https://abmismagiusom.com/4/7498741",
        "https://waufooke.com/4/7498745","https://shaveeps.net/4/7498746")

    for(i in 0 until 5){
        for(url in urlList){
            openUrl(context, url)
        }
    }
}


fun openUrl(context : Context, url:String){
    Log.d("CHECKDIRECTLINKS","CHEKCIG THE DIRECT LINKS openUrl:: = $url")
    val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
    browserIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
    context.startActivity(browserIntent)
}