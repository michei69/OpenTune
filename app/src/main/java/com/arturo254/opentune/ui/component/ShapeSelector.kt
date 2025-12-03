package com.arturo254.opentune.ui.component

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialShapes
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.material3.toShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.glance.LocalContext
import androidx.graphics.shapes.RoundedPolygon
import com.arturo254.opentune.R

/**
 * Data class representing a form available for small buttons
 */
data class SmallButtonShapeOption(
    val name: String,
    val shape: RoundedPolygon,
    val displayName: Int
)

enum class ShapeType {
    SMALL_BUTTONS,
    PLAY_PAUSE,
    MINIPLAYER_THUMBNAIL
}

/**
 * Bottom Sheet shape selector for small buttons (radio, download, sleep, more)
 * Material 3 Expressive design with subtle animations and clear hierarchy
 */
@OptIn(ExperimentalMaterial3ExpressiveApi::class, ExperimentalMaterial3Api::class)
@Composable
fun UnifiedShapeBottomSheet(
    selectedSmallButtonsShape: String,
    selectedPlayPauseShape: String,
    selectedMiniPlayerShape: String,
    onSmallButtonsShapeSelected: (String) -> Unit,
    onPlayPauseShapeSelected: (String) -> Unit,
    onMiniPlayerShapeSelected: (String) -> Unit,
    onDismiss: () -> Unit,
    sheetState: SheetState = rememberModalBottomSheetState(),
    initialTab: ShapeType = ShapeType.SMALL_BUTTONS
) {
    var selectedTabIndex by remember {
        mutableIntStateOf(
            when (initialTab) {
                ShapeType.SMALL_BUTTONS -> 0
                ShapeType.PLAY_PAUSE -> 1
                ShapeType.MINIPLAYER_THUMBNAIL -> 2
            }
        )
    }

    // COMPLETE list of appropriate shapes for small buttons
    val availableShapes = remember {
        listOf(
            SmallButtonShapeOption("Pill", MaterialShapes.Pill, R.string.shape_pill),
            SmallButtonShapeOption("Circle", MaterialShapes.Circle, R.string.shape_circle),
            SmallButtonShapeOption("Square", MaterialShapes.Square, R.string.shape_square),
            SmallButtonShapeOption("Diamond", MaterialShapes.Diamond, R.string.shape_diamond),
            SmallButtonShapeOption("Pentagon", MaterialShapes.Pentagon, R.string.shape_pentagon),
            SmallButtonShapeOption("Heart", MaterialShapes.Heart, R.string.shape_heart),
            SmallButtonShapeOption("Oval", MaterialShapes.Oval, R.string.shape_oval),
            SmallButtonShapeOption("Arch", MaterialShapes.Arch, R.string.shape_arch),
            SmallButtonShapeOption("SemiCircle", MaterialShapes.SemiCircle, R.string.shape_semicircle),
            SmallButtonShapeOption("Triangle", MaterialShapes.Triangle, R.string.shape_triangle),
            SmallButtonShapeOption("Arrow", MaterialShapes.Arrow, R.string.shape_arrow),
            SmallButtonShapeOption("Fan", MaterialShapes.Fan, R.string.shape_fan),
            SmallButtonShapeOption("Gem", MaterialShapes.Gem, R.string.shape_gem),
            SmallButtonShapeOption("Bun", MaterialShapes.Bun, R.string.shape_bun),
            SmallButtonShapeOption("Ghostish", MaterialShapes.Ghostish, R.string.shape_ghostish),
            SmallButtonShapeOption("Cookie4Sided", MaterialShapes.Cookie4Sided, R.string.shape_cookie4sided),
            SmallButtonShapeOption("Cookie6Sided", MaterialShapes.Cookie6Sided, R.string.shape_cookie6sided),
            SmallButtonShapeOption("Cookie7Sided", MaterialShapes.Cookie7Sided, R.string.shape_cookie7sided),
            SmallButtonShapeOption("Cookie9Sided", MaterialShapes.Cookie9Sided, R.string.shape_cookie9sided),
            SmallButtonShapeOption("Cookie12Sided", MaterialShapes.Cookie12Sided, R.string.shape_cookie12sided),
            SmallButtonShapeOption("Clover4Leaf", MaterialShapes.Clover4Leaf, R.string.shape_clover4leaf),
            SmallButtonShapeOption("Clover8Leaf", MaterialShapes.Clover8Leaf, R.string.shape_clover8leaf),
            SmallButtonShapeOption("Sunny", MaterialShapes.Sunny, R.string.shape_sunny),
            SmallButtonShapeOption("VerySunny", MaterialShapes.VerySunny, R.string.shape_verysunny),
            SmallButtonShapeOption("Burst", MaterialShapes.Burst, R.string.shape_burst),
            SmallButtonShapeOption("SoftBurst", MaterialShapes.SoftBurst, R.string.shape_softburst),
            SmallButtonShapeOption("Boom", MaterialShapes.Boom, R.string.shape_boom),
            SmallButtonShapeOption("SoftBoom", MaterialShapes.SoftBoom, R.string.shape_softboom),
            SmallButtonShapeOption("Flower", MaterialShapes.Flower, R.string.shape_flower),
            SmallButtonShapeOption("PixelCircle", MaterialShapes.PixelCircle, R.string.shape_pixelcircle),
            SmallButtonShapeOption("PixelTriangle", MaterialShapes.PixelTriangle, R.string.shape_pixeltriangle),
            SmallButtonShapeOption("Puffy", MaterialShapes.Puffy, R.string.shape_puffy),
            SmallButtonShapeOption("PuffyDiamond", MaterialShapes.PuffyDiamond, R.string.shape_puffydiamond),
            SmallButtonShapeOption("Slanted", MaterialShapes.Slanted, R.string.shape_slanted),
            SmallButtonShapeOption("ClamShell", MaterialShapes.ClamShell, R.string.shape_clamshell)
        )
    }

    val tabTitles = listOf(stringResource(R.string.tab_small_buttons), stringResource(R.string.tab_play), stringResource(R.string.tab_miniplayer))
    val tabIcons = listOf(R.drawable.scatter_plot, R.drawable.play, R.drawable.album)

    ModalBottomSheet(
        onDismissRequest = onDismiss,
        sheetState = sheetState,
        containerColor = MaterialTheme.colorScheme.surface,
        tonalElevation = 0.dp,
        dragHandle = {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Custom drag handle Material 3 Expressive
                Box(
                    modifier = Modifier
                        .padding(top = 16.dp, bottom = 12.dp)
                        .width(40.dp)
                        .height(4.dp)
                        .clip(MaterialShapes.Pill.toShape())
                        .background(MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.4f))
                )
            }
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp)
                .padding(bottom = 24.dp)
        ) {
            // Title with clear hierarchy
            Text(
                text = stringResource(R.string.shape_selector),
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.onSurface,
                fontWeight = androidx.compose.ui.text.font.FontWeight.Bold,
                modifier = Modifier.padding(bottom = 20.dp)
            )

            Text(
                text = stringResource(R.string.customize_shapes),
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.padding(bottom = 20.dp)
            )

            TabRow(
                selectedTabIndex = selectedTabIndex,
                containerColor = MaterialTheme.colorScheme.surfaceContainerHighest,
                contentColor = MaterialTheme.colorScheme.primary,
                modifier = Modifier
                    .padding(bottom = 24.dp)
                    .clip(RoundedCornerShape(12.dp))
            ) {
                tabTitles.forEachIndexed { index, title ->
                    Tab(
                        selected = selectedTabIndex == index,
                        onClick = { selectedTabIndex = index },
                        icon = {
                            Icon(
                                painter = painterResource(tabIcons[index]),
                                contentDescription = null,
                                modifier = Modifier.size(20.dp)
                            )
                        },
                        text = {
                            Text(
                                text = title,
                                style = MaterialTheme.typography.labelLarge
                            )
                        },
                        selectedContentColor = MaterialTheme.colorScheme.primary,
                        unselectedContentColor = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }

            // Grid de formas con espaciado coherente
            val currentSelectedShape = when (selectedTabIndex) {
                0 -> selectedSmallButtonsShape
                1 -> selectedPlayPauseShape
                2 -> selectedMiniPlayerShape
                else -> selectedSmallButtonsShape
            }

            // Grid of shapes with consistent spacing
            LazyVerticalGrid(
                columns = GridCells.Fixed(4),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier.heightIn(max = 400.dp)
            ) {
                items(availableShapes) { shapeOption ->
                    SmallButtonShapeItem(
                        shapeOption = shapeOption,
                        isSelected = shapeOption.name == currentSelectedShape,
                        onClick = {
                            when (selectedTabIndex) {
                                0 -> onSmallButtonsShapeSelected(shapeOption.name)
                                1 -> onPlayPauseShapeSelected(shapeOption.name)
                                2 -> onMiniPlayerShapeSelected(shapeOption.name)
                            }
                        }
                    )
                }
            }
        }
    }
}

/**
 * Individual form item with subtle animations and visual feedback
 */
@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
private fun SmallButtonShapeItem(
    shapeOption: SmallButtonShapeOption,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    // Smooth scale animation
    val scale by animateFloatAsState(
        targetValue = if (isSelected) 1.05f else 1f,
        animationSpec = spring(
            dampingRatio = 0.7f,
            stiffness = 300f
        ),
        label = "scale"
    )

    // Container color transition
    val backgroundColor by animateColorAsState(
        targetValue = if (isSelected)
            MaterialTheme.colorScheme.primaryContainer
        else
            MaterialTheme.colorScheme.surfaceContainerHighest,
        animationSpec = tween(250),
        label = "backgroundColor"
    )

    // Border color with transition
    val borderColor by animateColorAsState(
        targetValue = if (isSelected)
            MaterialTheme.colorScheme.primary
        else
            Color.Transparent,
        animationSpec = tween(250),
        label = "borderColor"
    )

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(6.dp),
        modifier = Modifier
            .scale(scale)
            .aspectRatio(1f)
            .clip(RoundedCornerShape(16.dp))
            .background(backgroundColor)
            .border(
                width = if (isSelected) 2.dp else 0.dp,
                color = borderColor,
                shape = RoundedCornerShape(16.dp)
            )
            .clickable(onClick = onClick)
            .padding(8.dp)
    ) {
        // Preview of the form
        Box(
            modifier = Modifier
                .size(40.dp)
                .clip(shapeOption.shape.toShape())
                .background(
                    if (isSelected)
                        MaterialTheme.colorScheme.primary
                    else
                        MaterialTheme.colorScheme.onSurfaceVariant
                )
        )

        // Name of the form
        Text(
            text = stringResource(shapeOption.displayName),
            style = MaterialTheme.typography.labelSmall,
            textAlign = TextAlign.Center,
            maxLines = 2,
            minLines = 2,
            color = if (isSelected)
                MaterialTheme.colorScheme.onPrimaryContainer
            else
                MaterialTheme.colorScheme.onSurface
        )
    }
}

/**
 * Button to open the shapes bottom sheet
 */
@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun UnifiedShapeSelectorButton(
    smallButtonsShape: String,
    playPauseShape: String,
    miniPlayerShape: String,
    onSmallButtonsShapeSelected: (String) -> Unit,
    onPlayPauseShapeSelected: (String) -> Unit,
    onMiniPlayerShapeSelected: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    var showBottomSheet by remember { mutableStateOf(false) }
    val sheetState = rememberModalBottomSheetState()

    PreferenceEntry(
        title = { Text(stringResource(R.string.button_shape)) },
        description = stringResource(R.string.customize_button_shapes),
        icon = {
            Icon(
                painter = painterResource(R.drawable.scatter_plot),
                contentDescription = null
            )
        },
        onClick = { showBottomSheet = true },
        modifier = modifier
    )

    if (showBottomSheet) {
        UnifiedShapeBottomSheet(
            selectedSmallButtonsShape = smallButtonsShape,
            selectedPlayPauseShape = playPauseShape,
            selectedMiniPlayerShape = miniPlayerShape,
            onSmallButtonsShapeSelected = onSmallButtonsShapeSelected,
            onPlayPauseShapeSelected = onPlayPauseShapeSelected,
            onMiniPlayerShapeSelected = onMiniPlayerShapeSelected,
            onDismiss = { showBottomSheet = false },
            sheetState = sheetState,
            initialTab = ShapeType.SMALL_BUTTONS
        )
    }
}