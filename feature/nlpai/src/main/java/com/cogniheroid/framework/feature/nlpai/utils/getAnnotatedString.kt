package com.cogniheroid.framework.feature.nlpai.utils

import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import com.cogniheroid.framework.ui.theme.Dimensions

fun getAnnotatedString(text: String): AnnotatedString {
    val boldRegex = Regex("(?<!\\*)\\*\\*(?!\\*).*?(?<!\\*)\\*\\*(?!\\*)")

    val boldKeywords: Sequence<MatchResult> = boldRegex.findAll(text)

    val boldIndexes = mutableListOf<Pair<Int, Int>>()
    boldKeywords.map {
        boldIndexes.add(Pair(it.range.first, it.range.last - 2))
    }

    val newText = text.replace("**", "")

    return buildAnnotatedString {
        append(newText)

        // Add bold style to keywords that has to be bold
        boldIndexes.forEach {
            addStyle(
                style = SpanStyle(
                    fontWeight = FontWeight.Bold,
                    fontSize = Dimensions.primaryFontSize

                ),
                start = it.first,
                end = it.second
            )

        }
    }
}