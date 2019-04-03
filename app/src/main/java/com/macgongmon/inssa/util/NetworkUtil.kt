package com.macgongmon.inssa.util

import android.text.Html
import android.text.Spanned

fun fromHtml(source: String): Spanned {
    return if (android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.N) {

        Html.fromHtml(source)
    } else Html.fromHtml(source, Html.FROM_HTML_MODE_LEGACY)
}