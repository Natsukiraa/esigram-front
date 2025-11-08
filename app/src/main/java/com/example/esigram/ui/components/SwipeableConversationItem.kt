import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.esigram.domains.models.Conversation
import com.example.esigram.ui.components.ConversationItem
import kotlinx.coroutines.launch
import kotlin.math.roundToInt

@Composable
fun SwipeableConversationItem(
    conversation: Conversation,
    currentUserId: String,
    onOpenMessage: (String) -> Unit,
    onDelete: () -> Unit,
    onPin: (Conversation) -> Unit
) {
    val buttonWidthDp = 64.dp
    val buttonCount = 3
    val textSize = 10.sp
    val scope = rememberCoroutineScope()

    val maxSwipe = with(LocalDensity.current) { buttonWidthDp.toPx() * buttonCount }
    val offsetX = remember { Animatable(0f) }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
            .height(64.dp)
    ) {
        Row(
            modifier = Modifier
                .align(Alignment.CenterEnd)
                .fillMaxHeight()
        ) {
            Box(
                modifier = Modifier
                    .width(buttonWidthDp)
                    .fillMaxHeight()
                    .background(MaterialTheme.colorScheme.primaryContainer)
                    .clickable {
                        scope.launch {
                            offsetX.animateTo(0f, tween(200))
                        }
                        onPin(conversation)
                    },
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Épingler",
                    color = MaterialTheme.colorScheme.onPrimaryContainer,
                    style = MaterialTheme.typography.labelSmall,
                    fontSize = textSize

                )
            }

            Box(
                modifier = Modifier
                    .width(buttonWidthDp)
                    .fillMaxHeight()
                    .background(MaterialTheme.colorScheme.secondaryContainer)
                    .clickable {
                        scope.launch {
                            offsetX.animateTo(0f, tween(200))
                        }
                    },
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Archiver",
                    color = MaterialTheme.colorScheme.onSecondaryContainer,
                    style = MaterialTheme.typography.labelSmall,
                    fontSize = textSize
                )
            }

            Box(
                modifier = Modifier
                    .width(buttonWidthDp)
                    .fillMaxHeight()
                    .background(MaterialTheme.colorScheme.errorContainer)
                    .clickable {
                        scope.launch {
                            offsetX.animateTo(0f, tween(200))
                        }
                        onDelete()
                    },
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Supprimer",
                    color = MaterialTheme.colorScheme.onErrorContainer,
                    style = MaterialTheme.typography.labelSmall,
                    fontSize = textSize
                )
            }
        }

        Box(
            modifier = Modifier
                .offset { IntOffset(offsetX.value.roundToInt(), 0) }
                .pointerInput(Unit) {
                    detectDragGestures(
                        onDrag = { change, dragAmount ->
                            change.consume()
                            val newOffset = (offsetX.value + dragAmount.x)
                                .coerceIn(-maxSwipe, 0f)
                            scope.launch { offsetX.snapTo(newOffset) }
                        },
                        onDragEnd = {
                            scope.launch {
                                if (offsetX.value < -maxSwipe * 0.5f) {
                                    offsetX.animateTo(-maxSwipe, tween(200))
                                } else {
                                    offsetX.animateTo(0f, tween(200))
                                }
                            }
                        }
                    )
                }
                .background(
                    MaterialTheme.colorScheme.surface,
                    shape = RoundedCornerShape(12.dp)
                )
                .fillMaxWidth()
        ) {
            ConversationItem(
                conversation = conversation,
                currentUserId = currentUserId,
                modifier = Modifier.fillMaxWidth()
            ) {
                if (offsetX.value == 0f) onOpenMessage(conversation.id)
            }
        }
    }
}