package com.cogniheroid.framework.ui.component

import android.content.Context
import android.view.View
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintLayoutScope
import androidx.constraintlayout.compose.Dimension

@Composable
fun AdUIContainer(modifier : Modifier, content: @Composable (Modifier) -> Unit,
                  bannerAd2: (Context) -> View){
    ConstraintLayout(modifier = modifier) {
        val (banner1, container, banner2) = createRefs()

        /*AvengBannerLayout(modifier = Modifier
            .constrainAs(banner1) {
                top.linkTo(parent.top)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
                width = Dimension.fillToConstraints
            }
            .fillMaxWidth()) {
            avengerAd.getAdMobBannerView(context = context, NLPAIUtils.getEquationBannerAd1())
        }*/

        content(Modifier
            .constrainAs(container) {
                top.linkTo(parent.top)
                bottom.linkTo(banner2.top)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
                height = Dimension.fillToConstraints
                width = Dimension.fillToConstraints
            })

        AvengBannerLayout(modifier = Modifier
            .constrainAs(banner2) {
                bottom.linkTo(parent.bottom)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
                width = Dimension.fillToConstraints
            }
            .fillMaxWidth()) {
            bannerAd2(it)
        }



    }
}