package com.example.esigram.ui.components.friends

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.esigram.domains.models.FriendRequest

@Composable
fun FriendRequestList(
    modifier: Modifier = Modifier,
    inboundRequests: List<FriendRequest>,
    outboundRequests: List<FriendRequest>,
    onAcceptClick: (FriendRequest) -> Unit,
    onDeclineClick: (FriendRequest) -> Unit
) {
    var selectedTab by remember { mutableIntStateOf(0) }

    val tabs = listOf("Inbound", "Outbound")

    Column(modifier = modifier.fillMaxWidth()) {
        TabRow(selectedTabIndex = selectedTab) {
            tabs.forEachIndexed { index, title ->
                Tab(
                    selected = selectedTab == index,
                    onClick = { selectedTab = index },
                    text = { Text(title) }
                )
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        when (selectedTab) {
            0 -> RequestListContent(
                requests = inboundRequests,
                emptyText = "No inbound requests",
                onAcceptClick = onAcceptClick,
                onDeclineClick = onDeclineClick,
                outbound = false
            )

            1 -> RequestListContent(
                requests = outboundRequests,
                emptyText = "No outbound requests",
                onAcceptClick = onAcceptClick,
                onDeclineClick = onDeclineClick,
                outbound = true
            )
        }
    }
}

@Composable
private fun RequestListContent(
    requests: List<FriendRequest>,
    outbound: Boolean,
    emptyText: String,
    onAcceptClick: (FriendRequest) -> Unit,
    onDeclineClick: (FriendRequest) -> Unit
) {
    if (requests.isEmpty()) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(emptyText, style = MaterialTheme.typography.bodyMedium)
        }
    } else {
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp)
        ) {
            items(requests) { request ->
                if (!outbound) {
                    InboundFriendRequestItem(
                        request = request,
                        onAcceptClick = onAcceptClick,
                        onDeclineClick = onDeclineClick
                    )
                } else {
                    OutboundFriendRequestItem(
                        request = request,
                        onDeclineClick = onDeclineClick
                    )
                }
            }
        }
    }
}
