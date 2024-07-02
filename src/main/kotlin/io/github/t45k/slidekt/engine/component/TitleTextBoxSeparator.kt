package io.github.t45k.slidekt.engine.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp

@Composable
fun TitleTextBoxSeparator(slideHeight: Dp) = Box(Modifier.fillMaxWidth().padding(vertical = slideHeight / 72))
