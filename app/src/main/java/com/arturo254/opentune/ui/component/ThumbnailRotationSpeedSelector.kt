package com.arturo254.opentune.ui.component

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ArrowForward
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.toShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.media3.common.Player
import androidx.media3.common.Player.STATE_ENDED
import coil.compose.AsyncImage
import com.arturo254.opentune.R
import com.arturo254.opentune.constants.DarkModeKey
import com.arturo254.opentune.constants.DefaultMiniPlayerThumbnailShape
import com.arturo254.opentune.constants.DefaultMiniPlayerThumbnailSpeed
import com.arturo254.opentune.constants.DefaultPlayPauseButtonShape
import com.arturo254.opentune.constants.DefaultPlayerButtonSpeed
import com.arturo254.opentune.constants.MiniPlayerThumbnailShapeKey
import com.arturo254.opentune.constants.MiniPlayerThumbnailSpeed
import com.arturo254.opentune.constants.PlayPauseButtonShapeKey
import com.arturo254.opentune.constants.PlayerButtonSpeed
import com.arturo254.opentune.constants.PureBlackKey
import com.arturo254.opentune.extensions.togglePlayPause
import com.arturo254.opentune.ui.screens.settings.DarkMode
import com.arturo254.opentune.utils.MorphPolygonShape
import com.arturo254.opentune.utils.getMiniPlayerThumbnailShape
import com.arturo254.opentune.utils.getPlayPauseShape
import com.arturo254.opentune.utils.rememberEnumPreference
import com.arturo254.opentune.utils.rememberPreference
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlin.math.absoluteValue
import kotlin.math.roundToInt

@Composable
fun ThumbnailRotationSpeedSelector() {
    var showDialog by remember { mutableStateOf(false) }
    val (playPauseSpeedState, onPlayPauseSpeedStateChange) = rememberPreference(
        key = PlayerButtonSpeed,
        defaultValue = DefaultPlayerButtonSpeed
    )
    val (miniPlayerThumbnailSpeedState, onMiniPlayerThumbnailSpeedStateChange) = rememberPreference(
        key = MiniPlayerThumbnailSpeed,
        defaultValue = DefaultMiniPlayerThumbnailSpeed
    )

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .clickable(
                enabled = true,
                onClick = { showDialog = true }
            ),
        colors = CardDefaults.cardColors(
            containerColor = Color.Transparent
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp, vertical = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(
                        MaterialTheme.colorScheme.primary.copy(alpha = 0.1f)
                    ),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.album),
                    contentDescription = null,
                    modifier = Modifier.size(24.dp),
                )
            }
            Spacer(modifier = Modifier.width(16.dp))
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = stringResource(
                        id = R.string.customize_rotation_speed,
                    ),

                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    style = MaterialTheme.typography.titleMedium,
                )
            }
            Spacer(modifier = Modifier.width(8.dp))
            Icon(
                imageVector = Icons.AutoMirrored.Outlined.ArrowForward,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.size(20.dp)
            )
        }
    }

    if (showDialog) {
        ThumbnailRotationspeedModal(
            playPauseSpeedState,
            miniPlayerThumbnailSpeedState,
            onDismiss = { showDialog = false },
            onChange = { playPauseSpeedState, miniPlayerThumbnailSpeedState ->
                onPlayPauseSpeedStateChange(playPauseSpeedState)
                onMiniPlayerThumbnailSpeedStateChange(miniPlayerThumbnailSpeedState)
            }
        )
    }
}

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun ThumbnailRotationspeedModal(
    initialPlayerSpeed: Float,
    initialMiniPlayerSpeed: Float,
    onDismiss: () -> Unit,
    onChange: (Float, Float) -> Unit,
) {
    var playerSpeed by remember { mutableStateOf(initialPlayerSpeed) }
    var miniPlayerSpeed by remember { mutableStateOf(initialMiniPlayerSpeed) }

    var customValuePlayer by remember { mutableStateOf(initialPlayerSpeed.toString()) }
    var customValueMiniPlayer by remember { mutableStateOf(initialMiniPlayerSpeed.toString()) }

    val rotationAngleMiniPlayer = remember { Animatable(0f) }
    LaunchedEffect(miniPlayerSpeed) {
        rotationAngleMiniPlayer.animateTo(
            targetValue = rotationAngleMiniPlayer.value + 360f,
            animationSpec = infiniteRepeatable(
                animation = tween((miniPlayerSpeed * 1000).toInt(), easing = LinearEasing),
                repeatMode = RepeatMode.Restart
            )
        )
    }
    val rotationAnglePlayer = remember { Animatable(0f) }
    LaunchedEffect(playerSpeed) {
        rotationAnglePlayer.animateTo(
            targetValue = rotationAnglePlayer.value + 360f,
            animationSpec = infiniteRepeatable(
                animation = tween((playerSpeed * 1000).toInt(), easing = LinearEasing),
                repeatMode = RepeatMode.Restart
            )
        )
    }

    val isSystemInDarkTheme = isSystemInDarkTheme()
    val darkTheme by rememberEnumPreference(DarkModeKey, defaultValue = DarkMode.AUTO)
    val pureBlack by rememberPreference(PureBlackKey, defaultValue = false)
    val useDarkTheme = remember(darkTheme, isSystemInDarkTheme) {
        if (darkTheme == DarkMode.AUTO) isSystemInDarkTheme else darkTheme == DarkMode.ON
    }
    val miniPlayerBackgroundColor = when {
        useDarkTheme && pureBlack -> Color.Black.copy(alpha = 0.95f)
        else -> MaterialTheme.colorScheme.surfaceContainer.copy(alpha = 0.95f)
    }
    val miniPlayerThumbnailShapeState = rememberPreference(
        key = MiniPlayerThumbnailShapeKey,
        defaultValue = DefaultMiniPlayerThumbnailShape
    )
    val miniPlayerThumbnailShape = remember(miniPlayerThumbnailShapeState.value) {
        getMiniPlayerThumbnailShape(miniPlayerThumbnailShapeState.value)
    }
    val playPauseShapeState = rememberPreference(
        key = PlayPauseButtonShapeKey,
        defaultValue = DefaultPlayPauseButtonShape
    )
    val playPauseShape = remember(playPauseShapeState.value) {
        getPlayPauseShape(playPauseShapeState.value)
    }
    val modifier = Modifier

    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(
            dismissOnBackPress = true,
            dismissOnClickOutside = true,
            usePlatformDefaultWidth = false
        )
    ) {
        BoxWithConstraints {
            val screenWidth = maxWidth
            val dialogWidth = when {
                screenWidth > 840.dp -> 0.5f
                screenWidth > 600.dp -> 0.7f
                else -> 0.95f
            }

            Surface(
                modifier = Modifier
                    .fillMaxWidth(dialogWidth)
                    .wrapContentHeight()
                    .clip(RoundedCornerShape(30.dp))
                    .padding(16.dp),
                shape = MaterialTheme.shapes.large,
                color = MaterialTheme.colorScheme.surface,
                tonalElevation = 6.dp
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = stringResource(R.string.change_player_rotation_duration),
                        style = MaterialTheme.typography.titleLarge,
                        color = MaterialTheme.colorScheme.onSurface,
                        fontWeight = FontWeight.Bold,
                        fontSize = 28.sp,
                        modifier = Modifier.padding(bottom = 20.dp)
                    )

                    // MiniPlayer view
                    Box(
                        modifier = modifier
                            .fillMaxWidth()
                            .height(80.dp)
                            .windowInsetsPadding(WindowInsets.systemBars.only(WindowInsetsSides.Horizontal))
                            .padding(horizontal = 16.dp, vertical = 8.dp)
                            .background(Color.Transparent)
                    ) {
                        // Main MiniPlayer box that moves with swipe
                        Surface(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(64.dp)
                                .shadow(
                                    elevation = 8.dp,
                                    shape = RoundedCornerShape(32.dp),
                                    clip = false
                                ),
                            tonalElevation = 2.dp,
                            shape = RoundedCornerShape(32.dp),
                            color = Color.Transparent
                        ) {
                            Box(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .background(miniPlayerBackgroundColor)
                            ) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .padding(horizontal = 12.dp, vertical = 8.dp),
                                ) {
                                    // Play/Pause button with circular progress indicator (left side)
                                    Box(
                                        contentAlignment = Alignment.Center,
                                        modifier = Modifier.size(48.dp)
                                    ) {
                                        // Circular progress indicator around the play button
                                        CircularProgressIndicator(
                                            progress = .4f,
                                            modifier = Modifier.size(48.dp),
                                            color = MaterialTheme.colorScheme.primary,
                                            strokeWidth = 3.dp,
                                            trackColor = MaterialTheme.colorScheme.outline.copy(alpha = 0.2f)
                                        )

                                        // Play/Pause button with thumbnail background
                                        Box(
                                            contentAlignment = Alignment.Center,
                                            modifier = Modifier
                                                .size(40.dp)
                                                .rotate(rotationAngleMiniPlayer.value) // Rotates the thumbnail when playing
                                                .clip(
                                                    miniPlayerThumbnailShape.toShape()
                                                )
                                                .border(
                                                    width = 1.dp,
                                                    color = MaterialTheme.colorScheme.outline.copy(alpha = 0.3f),
                                                    shape = miniPlayerThumbnailShape.toShape()
                                                )
                                        ) {
                                            // Thumbnail background
                                            Image(
                                                painter = painterResource(id = R.drawable.previewalbum),
                                                contentDescription = null,
                                                contentScale = ContentScale.Crop,
                                                modifier = Modifier
                                                    .fillMaxSize()
                                                    .clip(miniPlayerThumbnailShape.toShape())
                                            )
                                        }
                                    }

                                    Spacer(modifier = Modifier.width(16.dp))

                                    // Song info - takes most space in the middle
                                    Column(
                                        modifier = Modifier.weight(1f),
                                        verticalArrangement = Arrangement.Center
                                    ) {
                                        AnimatedContent(
                                            targetState = stringResource(R.string.example_song_title),
                                            transitionSpec = { fadeIn() togetherWith fadeOut() },
                                            label = "",
                                        ) { title ->
                                            Text(
                                                text = title,
                                                color = MaterialTheme.colorScheme.onSurface,
                                                fontSize = 14.sp,
                                                fontWeight = FontWeight.Medium,
                                                maxLines = 1,
                                                overflow = TextOverflow.Ellipsis,
                                                modifier = Modifier.basicMarquee(),
                                            )
                                        }

                                        AnimatedContent(
                                            targetState = stringResource(R.string.example_song_artist),
                                            transitionSpec = { fadeIn() togetherWith fadeOut() },
                                            label = "",
                                        ) { artists ->
                                            Text(
                                                text = artists,
                                                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f),
                                                fontSize = 12.sp,
                                                maxLines = 1,
                                                overflow = TextOverflow.Ellipsis,
                                                modifier = Modifier.basicMarquee(),
                                            )
                                        }
                                    }

                                    Spacer(modifier = Modifier.width(8.dp))

                                    // Like button
                                    IconButton(
                                        onClick = { },
                                        modifier = Modifier.size(36.dp)
                                    ) {
                                        Icon(
                                            painter = painterResource(
                                                R.drawable.favorite
                                            ),
                                            contentDescription = "Like",
                                            tint = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f),
                                            modifier = Modifier.size(18.dp)
                                        )
                                    }

                                    // Skip next button (right side)
                                    IconButton(
                                        enabled = true,
                                        onClick = { },
                                        modifier = Modifier.size(36.dp)
                                    ) {
                                        Icon(
                                            painter = painterResource(R.drawable.skip_next),
                                            contentDescription = null,
                                            tint = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f),
                                            modifier = Modifier.size(18.dp)
                                        )
                                    }
                                }
                            }
                        }
                    }

                    // custom field for miniplayer rotation speed
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        OutlinedTextField(
                            value = customValueMiniPlayer,
                            onValueChange = { newValue ->
                                // Only accept numbers
                                if (newValue.isEmpty() || newValue.all { it.isDigit() || it == '.' || it == ',' }) {
                                    customValueMiniPlayer = newValue
                                    newValue.replace(',', '.').toFloatOrNull()?.let {
                                        miniPlayerSpeed = if (it > 0.1) it else miniPlayerSpeed
                                    }
                                }
                            },
                            modifier = Modifier
                                .weight(1f)
                                .height(64.dp),
                            enabled = true,
                            label = {
                                Text(
                                    text = stringResource(id = R.string.custom_value),
                                    style = MaterialTheme.typography.bodyMedium
                                )
                            },
                            singleLine = true,
                            keyboardOptions = KeyboardOptions(
                                keyboardType = KeyboardType.Number
                            ),
                            trailingIcon = {
                                Text(
                                    text = "sec",
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            },
                            shape = MaterialTheme.shapes.small
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    // player button
                    Box(
                        modifier =
                            Modifier
                                .size(85.dp)
                                .rotate(rotationAnglePlayer.value) // Rotates the play/pause button when playing
                                .clip(playPauseShape.toShape())
                                .background(MaterialTheme.colorScheme.secondary),
                    ) {
                        Image(
                            painter =
                                painterResource(
                                    R.drawable.pause,
                                ),
                            contentDescription = null,
                            colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onSecondary),
                            modifier =
                                Modifier
                                    .align(Alignment.Center)
                                    .size(36.dp)
                                    .rotate(-rotationAnglePlayer.value),
                        )
                    }

                    // custom field for player rotation speed
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        OutlinedTextField(
                            value = customValuePlayer,
                            onValueChange = { newValue ->
                                // Only accept numbers
                                if (newValue.isEmpty() || newValue.all { it.isDigit() || it == '.' || it == ',' }) {
                                    customValuePlayer = newValue
                                    newValue.replace(',', '.').toFloatOrNull()?.let {
                                        playerSpeed = if (it >= 0.1) it else playerSpeed
                                    }
                                }
                            },
                            modifier = Modifier
                                .weight(1f)
                                .height(64.dp),
                            enabled = true,
                            label = {
                                Text(
                                    text = stringResource(id = R.string.custom_value),
                                    style = MaterialTheme.typography.bodyMedium
                                )
                            },
                            singleLine = true,
                            keyboardOptions = KeyboardOptions(
                                keyboardType = KeyboardType.Number
                            ),
                            trailingIcon = {
                                Text(
                                    text = "sec",
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            },
                            shape = MaterialTheme.shapes.small
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    // Action buttons with improved UX
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(16.dp, Alignment.End),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        TextButton(
                            onClick = onDismiss,
                            modifier = Modifier.heightIn(min = 48.dp)
                        ) {
                            Text(
                                text = stringResource(id = R.string.cancel),
                                style = MaterialTheme.typography.labelLarge
                            )
                        }

                        Button(
                            onClick = {
                                CoroutineScope(Dispatchers.IO).launch {
                                    onChange(playerSpeed, miniPlayerSpeed)
                                    onDismiss()
                                }
                            },
                            modifier = Modifier.heightIn(min = 48.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = MaterialTheme.colorScheme.primary,
                                contentColor = MaterialTheme.colorScheme.onPrimary
                            )
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(
                                    painter = painterResource(R.drawable.check),
                                    contentDescription = null,
                                    modifier = Modifier.size(18.dp)
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(
                                    text = stringResource(id = R.string.apply),
                                    style = MaterialTheme.typography.labelLarge
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}