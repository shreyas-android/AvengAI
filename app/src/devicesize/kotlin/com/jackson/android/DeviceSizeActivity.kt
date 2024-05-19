package com.jackson.android

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.absoluteOffset
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jackson.android.theme.ComposeUITheme

class DeviceSizeActivity: AppCompatActivity() {

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState : Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ComposeView(this).apply {
                ComposeUITheme {
                    Column {
                        val colors = TopAppBarDefaults.smallTopAppBarColors(
                            containerColor = MaterialTheme.colorScheme.surface,
                        )
                        Surface(tonalElevation = 3.dp) {
                            TopAppBar(modifier = Modifier.statusBarsPadding(), colors = colors, title = {
                                Text(
                                    text = stringResource(id = R.string.app_name),
                                    fontSize = 16.sp, color = MaterialTheme.colorScheme.onSurface
                                )
                            })
                        }
                        DeviceInfoContainer()
                    }
                }
            }
        }

    }

    @Composable
    fun DeviceInfoContainer(){
        val tapOffset = remember{
            mutableStateOf(Offset(0f,0f))
        }
        BoxWithConstraints(modifier = Modifier
            .pointerInput(Unit) {
                detectTapGestures {
                    tapOffset.value = it
                }
            }
            .background(MaterialTheme.colorScheme.background)
            .fillMaxSize()) {

            val context = LocalContext.current


            val density = LocalDensity.current
            with(density) {
                val statusBar =
                    WindowInsets.statusBars.getTop(this) + WindowInsets.statusBars.getBottom(this)
                val navigationBar = WindowInsets.navigationBars.getTop(
                    this) + WindowInsets.navigationBars.getBottom(this)

                val width = maxWidth
                val height = context.resources.displayMetrics.heightPixels + statusBar + navigationBar

                Column(modifier = Modifier.padding(top = 16.dp), horizontalAlignment = Alignment.CenterHorizontally) {

                    Text(
                        modifier = Modifier, text = "${width.toPx().toInt()} pixels",
                        fontSize = 16.sp, color = MaterialTheme.colorScheme.onSurface)

                    Spacer(
                        modifier = Modifier
                            .statusBarsPadding()
                            .padding(top = 8.dp)
                            .width(width)
                            .height(1.dp)
                            .background(MaterialTheme.colorScheme.primary))
                }

                Row(modifier = Modifier.padding(start = 16.dp), verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        modifier = Modifier
                            .rotate(-90f), text = "${height.toInt()} pixels",
                        fontSize = 16.sp, color = MaterialTheme.colorScheme.onSurface)

                    Spacer(
                        modifier = Modifier
                            .padding(start = 8.dp)
                            .width(1.dp)
                            .height(height.toDp())
                            .background(MaterialTheme.colorScheme.primary))
                }


                Box(modifier = Modifier.padding(start = 16.dp).absoluteOffset(width/4, height.toDp()/4)) {
                    DataContainer(tapOffset.value, statusBar, navigationBar)
                }
            }


        }
    }

    @Composable
    fun Density.DataContainer(tapOffset : Offset, statusBar : Int, navigationBar : Int) {

            Column {

                Row(modifier = Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        modifier = Modifier, text = "Status bar",
                        fontSize = 16.sp, color = MaterialTheme.colorScheme.onSurface, fontWeight = FontWeight.Medium)

                    Text(
                        modifier = Modifier,
                        text = " = $statusBar pixels", fontSize = 16.sp,
                        color = MaterialTheme.colorScheme.onSurface)
                }

                Row(modifier = Modifier.padding(start = 16.dp, top = 4.dp)) {
                    Text(
                        modifier = Modifier, text = "Navigation bar",
                        fontSize = 16.sp, color = MaterialTheme.colorScheme.onSurface, fontWeight = FontWeight.Medium)

                    Text(
                        modifier = Modifier,
                        text = " = $navigationBar pixels", fontSize = 16.sp,
                        color = MaterialTheme.colorScheme.onSurface)
                }


                    Text(
                        modifier = Modifier.padding(top = 36.dp, start = 16.dp).fillMaxWidth(), text = "Tap position",
                        fontSize = 18.sp, color = MaterialTheme.colorScheme.onSurface, fontWeight = FontWeight.SemiBold)



                        Row(modifier = Modifier.padding(start = 24.dp, top = 24.dp)) {
                            Text(
                                modifier = Modifier, text = "Left",
                                fontSize = 16.sp, color = MaterialTheme.colorScheme.onSurface, fontWeight = FontWeight.Medium)

                            Text(
                                modifier = Modifier,
                                text = " = ${tapOffset.x.toInt()} pixels", fontSize = 16.sp,
                                color = MaterialTheme.colorScheme.onSurface)
                        }

                        Row(modifier = Modifier.padding(start = 24.dp, top = 16.dp)) {
                            Text(
                                modifier = Modifier, text = "Top",
                                fontSize = 16.sp, color = MaterialTheme.colorScheme.onSurface, fontWeight = FontWeight.Medium)

                            Text(
                                modifier = Modifier,
                                text = " = ${tapOffset.y.toInt()} pixels", fontSize = 16.sp,
                                color = MaterialTheme.colorScheme.onSurface)
                        }


            }

    }
}

