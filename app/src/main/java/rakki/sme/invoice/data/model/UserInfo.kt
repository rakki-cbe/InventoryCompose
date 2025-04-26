package rakki.sme.invoice.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

data class UserInfo(val name: String, val userName: String, val password: String)

@Entity
data class CurrentUserData(
    val name: String,
    @PrimaryKey
    val userName: String,
    val credential: String
)
