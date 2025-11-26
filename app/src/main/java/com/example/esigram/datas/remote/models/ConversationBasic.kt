import com.example.esigram.datas.remote.models.UserConversationBasic
import com.example.esigram.domains.models.Message
import java.time.Instant

data class ConversationBasic(
    val id: String,
    val members: List<UserConversationBasic>,
    val coverImageId: String?,
    val lastMessage: Message?,
    val unreadCount: Int,
    val title: String?,
    val createdAt: Instant
)