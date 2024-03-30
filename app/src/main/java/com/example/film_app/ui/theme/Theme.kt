package com.example.compose
import android.app.Activity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat
import com.example.film_app.ui.theme.Typography
import com.example.film_app.ui.theme.backgroundDark
import com.example.film_app.ui.theme.backgroundLight
import com.example.film_app.ui.theme.errorContainerDark
import com.example.film_app.ui.theme.errorContainerLight
import com.example.film_app.ui.theme.errorDark
import com.example.film_app.ui.theme.errorLight
import com.example.film_app.ui.theme.inverseOnSurfaceDark
import com.example.film_app.ui.theme.inverseOnSurfaceLight
import com.example.film_app.ui.theme.inversePrimaryDark
import com.example.film_app.ui.theme.inversePrimaryLight
import com.example.film_app.ui.theme.inverseSurfaceDark
import com.example.film_app.ui.theme.inverseSurfaceLight
import com.example.film_app.ui.theme.onBackgroundDark
import com.example.film_app.ui.theme.onBackgroundLight
import com.example.film_app.ui.theme.onErrorContainerDark
import com.example.film_app.ui.theme.onErrorContainerLight
import com.example.film_app.ui.theme.onErrorDark
import com.example.film_app.ui.theme.onErrorLight
import com.example.film_app.ui.theme.onPrimaryContainerDark
import com.example.film_app.ui.theme.onPrimaryContainerLight
import com.example.film_app.ui.theme.onPrimaryDark
import com.example.film_app.ui.theme.onPrimaryLight
import com.example.film_app.ui.theme.onSecondaryContainerDark
import com.example.film_app.ui.theme.onSecondaryContainerLight
import com.example.film_app.ui.theme.onSecondaryDark
import com.example.film_app.ui.theme.onSecondaryLight
import com.example.film_app.ui.theme.onSurfaceDark
import com.example.film_app.ui.theme.onSurfaceLight
import com.example.film_app.ui.theme.onSurfaceVariantDark
import com.example.film_app.ui.theme.onSurfaceVariantLight
import com.example.film_app.ui.theme.onTertiaryContainerDark
import com.example.film_app.ui.theme.onTertiaryContainerLight
import com.example.film_app.ui.theme.onTertiaryDark
import com.example.film_app.ui.theme.onTertiaryLight
import com.example.film_app.ui.theme.outlineDark
import com.example.film_app.ui.theme.outlineLight
import com.example.film_app.ui.theme.outlineVariantDark
import com.example.film_app.ui.theme.outlineVariantLight
import com.example.film_app.ui.theme.primaryContainerDark
import com.example.film_app.ui.theme.primaryContainerLight
import com.example.film_app.ui.theme.primaryDark
import com.example.film_app.ui.theme.primaryLight
import com.example.film_app.ui.theme.scrimDark
import com.example.film_app.ui.theme.scrimLight
import com.example.film_app.ui.theme.secondaryContainerDark
import com.example.film_app.ui.theme.secondaryContainerLight
import com.example.film_app.ui.theme.secondaryDark
import com.example.film_app.ui.theme.secondaryLight
import com.example.film_app.ui.theme.surfaceDark
import com.example.film_app.ui.theme.surfaceDimDark
import com.example.film_app.ui.theme.surfaceDimLight
import com.example.film_app.ui.theme.surfaceLight
import com.example.film_app.ui.theme.surfaceVariantDark
import com.example.film_app.ui.theme.surfaceVariantLight
import com.example.film_app.ui.theme.tertiaryContainerDark
import com.example.film_app.ui.theme.tertiaryContainerLight
import com.example.film_app.ui.theme.tertiaryDark
import com.example.film_app.ui.theme.tertiaryLight

private val lightScheme = lightColorScheme(
    primary = primaryLight,
    onPrimary = onPrimaryLight,
    primaryContainer = primaryContainerLight,
    onPrimaryContainer = onPrimaryContainerLight,
    secondary = secondaryLight,
    onSecondary = onSecondaryLight,
    secondaryContainer = secondaryContainerLight,
    onSecondaryContainer = onSecondaryContainerLight,
    tertiary = tertiaryLight,
    onTertiary = onTertiaryLight,
    tertiaryContainer = tertiaryContainerLight,
    onTertiaryContainer = onTertiaryContainerLight,
    error = errorLight,
    onError = onErrorLight,
    errorContainer = errorContainerLight,
    onErrorContainer = onErrorContainerLight,
    background = backgroundLight,
    onBackground = onBackgroundLight,
    surface = surfaceLight,
    onSurface = onSurfaceLight,
    surfaceVariant = surfaceVariantLight,
    onSurfaceVariant = onSurfaceVariantLight,
    outline = outlineLight,
    outlineVariant = outlineVariantLight,
    scrim = scrimLight,
    inverseSurface = inverseSurfaceLight,
    inverseOnSurface = inverseOnSurfaceLight,
    inversePrimary = inversePrimaryLight,
    surfaceTint = surfaceDimLight,
//    surfaceBright = surfaceBrightLight,
//    surfaceContainerLowest = surfaceContainerLowestLight,
//    surfaceContainerLow = surfaceContainerLowLight,
//    surfaceContainer = surfaceContainerLight,
//    surfaceContainerHigh = surfaceContainerHighLight,
//    surfaceContainerHighest = surfaceContainerHighestLight,
)

private val darkScheme = darkColorScheme(
    primary = primaryDark,
    onPrimary = onPrimaryDark,
    primaryContainer = primaryContainerDark,
    onPrimaryContainer = onPrimaryContainerDark,
    secondary = secondaryDark,
    onSecondary = onSecondaryDark,
    secondaryContainer = secondaryContainerDark,
    onSecondaryContainer = onSecondaryContainerDark,
    tertiary = tertiaryDark,
    onTertiary = onTertiaryDark,
    tertiaryContainer = tertiaryContainerDark,
    onTertiaryContainer = onTertiaryContainerDark,
    error = errorDark,
    onError = onErrorDark,
    errorContainer = errorContainerDark,
    onErrorContainer = onErrorContainerDark,
    background = backgroundDark,
    onBackground = onBackgroundDark,
    surface = surfaceDark,
    onSurface = onSurfaceDark,
    surfaceVariant = surfaceVariantDark,
    onSurfaceVariant = onSurfaceVariantDark,
    outline = outlineDark,
    outlineVariant = outlineVariantDark,
    scrim = scrimDark,
    inverseSurface = inverseSurfaceDark,
    inverseOnSurface = inverseOnSurfaceDark,
    inversePrimary = inversePrimaryDark,
    surfaceTint = surfaceDimDark,
//    surfaceBright = surfaceBrightDark,
//    surfaceContainerLowest = surfaceContainerLowestDark,
//    surfaceContainerLow = surfaceContainerLowDark,
//    surfaceContainer = surfaceContainerDark,
//    surfaceContainerHigh = surfaceContainerHighDark,
//    surfaceContainerHighest = surfaceContainerHighestDark,
)

@Immutable
data class ColorFamily(
    val color: Color,
    val onColor: Color,
    val colorContainer: Color,
    val onColorContainer: Color
)

val unspecified_scheme = ColorFamily(
    Color.Unspecified, Color.Unspecified, Color.Unspecified, Color.Unspecified
)

@Composable
fun AppTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = false,
    content: @Composable() () -> Unit
) {
  val colorScheme = when {
//      dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
//          val context = LocalContext.current
//          if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
//      }
      
      darkTheme -> darkScheme
      else -> lightScheme
  }
  val view = LocalView.current
  if (!view.isInEditMode) {
    SideEffect {
      val window = (view.context as Activity).window
      window.statusBarColor = colorScheme.primary.toArgb()
      WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = darkTheme
    }
  }

  MaterialTheme(
    colorScheme = colorScheme,
    typography = Typography,
    content = content
  )
}

