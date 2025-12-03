package com.arturo254.opentune.utils

import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.geometry.Size
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.MaterialShapes
import androidx.compose.ui.graphics.Matrix
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.asComposePath
import androidx.compose.ui.unit.Density
import androidx.graphics.shapes.Morph
import androidx.graphics.shapes.RoundedPolygon
import androidx.graphics.shapes.toPath



@OptIn(ExperimentalMaterial3ExpressiveApi::class)
fun getSmallButtonShape(shapeName: String): RoundedPolygon {
    return when (shapeName) {
        "Pill" -> MaterialShapes.Pill
        "Circle" -> MaterialShapes.Circle
        "Square" -> MaterialShapes.Square
        "Diamond" -> MaterialShapes.Diamond
        "Pentagon" -> MaterialShapes.Pentagon
        "Heart" -> MaterialShapes.Heart
        "Oval" -> MaterialShapes.Oval
        "Arch" -> MaterialShapes.Arch
        "SemiCircle" -> MaterialShapes.SemiCircle
        "Triangle" -> MaterialShapes.Triangle
        "Arrow" -> MaterialShapes.Arrow
        "Fan" -> MaterialShapes.Fan
        "Gem" -> MaterialShapes.Gem
        "Bun" -> MaterialShapes.Bun
        "Ghostish" -> MaterialShapes.Ghostish
        "Cookie4Sided" -> MaterialShapes.Cookie4Sided
        "Cookie6Sided" -> MaterialShapes.Cookie6Sided
        "Cookie7Sided" -> MaterialShapes.Cookie7Sided
        "Cookie9Sided" -> MaterialShapes.Cookie9Sided
        "Cookie12Sided" -> MaterialShapes.Cookie12Sided
        "Clover4Leaf" -> MaterialShapes.Clover4Leaf
        "Clover8Leaf" -> MaterialShapes.Clover8Leaf
        "Sunny" -> MaterialShapes.Sunny
        "VerySunny" -> MaterialShapes.VerySunny
        "Burst" -> MaterialShapes.Burst
        "SoftBurst" -> MaterialShapes.SoftBurst
        "Boom" -> MaterialShapes.Boom
        "SoftBoom" -> MaterialShapes.SoftBoom
        "Flower" -> MaterialShapes.Flower
        "PixelCircle" -> MaterialShapes.PixelCircle
        "PixelTriangle" -> MaterialShapes.PixelTriangle
        "Puffy" -> MaterialShapes.Puffy
        "PuffyDiamond" -> MaterialShapes.PuffyDiamond
        "Slanted" -> MaterialShapes.Slanted
        "ClamShell" -> MaterialShapes.ClamShell
        else -> MaterialShapes.Circle
    }
}

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
fun getPlayPauseShape(shapeName: String): RoundedPolygon = getSmallButtonShape(shapeName)

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
fun getMiniPlayerThumbnailShape(shapeName: String): RoundedPolygon = getSmallButtonShape(shapeName)

// https://developer.android.com/develop/ui/compose/graphics/draw/shapes#morph-button
class MorphPolygonShape(
    private val morph: Morph,
    private val percentage: Float
) : Shape {

    private val matrix = Matrix()
    override fun createOutline(
        size: Size,
        layoutDirection: LayoutDirection,
        density: Density
    ): Outline {
        matrix.scale(size.width, size.height)

        val path = morph.toPath(progress = percentage).asComposePath()
        path.transform(matrix)
        return Outline.Generic(path)
    }
}