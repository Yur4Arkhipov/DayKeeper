package com.example.keepday.ui.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.absoluteOffset
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.rememberNestedScrollInteropConnection
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.keepday.domain.DayEvent

@Composable
fun TimelineTable(
    modifier: Modifier = Modifier,
    events: List<DayEvent>,
    pixelsPerMinute: Float = 3.0f,
    onEventClick: (DayEvent) -> Unit = {}
) {
    val density = LocalDensity.current
    val totalMinutes = 24 * 60
    val scrollHeightPx = totalMinutes * pixelsPerMinute
    val scrollHeightDp = with(density) { scrollHeightPx.toDp() }

    val scrollState = rememberScrollState()

    Column(
        modifier = modifier
            .height(scrollHeightDp)
            .verticalScroll(scrollState)
            .nestedScroll(rememberNestedScrollInteropConnection())
    ) {

        Box(
            modifier = modifier
                .height(scrollHeightDp)
                .verticalScroll(scrollState)
        ) {
            Column {
                repeat(24) { hour ->
                    val hourHeightPx = 60f * pixelsPerMinute

                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(with(density) { hourHeightPx.toDp() })
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(24.dp)
                                .align(Alignment.TopStart),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "%02d:00".format(hour),
                                modifier = Modifier
                                    .width(60.dp)
                                    .padding(end = 8.dp),
                                textAlign = TextAlign.End,
                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                                style = MaterialTheme.typography.bodySmall
                            )

                            HorizontalDivider(
                                modifier = Modifier.weight(1f),
                                thickness = 1.dp,
                                color = MaterialTheme.colorScheme.outlineVariant
                            )
                        }
                    }
                }
            }

            events.forEach { event ->

                val topOffsetDp = with(density) {
                    (event.startMinute * pixelsPerMinute).toDp()
                }

                val heightDp = with(density) {
                    (event.durationMinutes * pixelsPerMinute).toDp()
                }

                val minHeightToShowText = 40.dp

                Box(
                    modifier = Modifier
                        .absoluteOffset(y = topOffsetDp)
                        .padding(start = 64.dp, end = 8.dp)
                        .fillMaxWidth()
                        .height(heightDp)
                        .clip(RoundedCornerShape(8.dp))
                        .background(event.color)
                        .clickable { onEventClick(event) }
                ) {
                    if (heightDp >= minHeightToShowText) {
                        Text(
                            text = event.title,
                            modifier = Modifier.padding(8.dp),
                            color = Color.White,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                }
            }
        }
    }
}