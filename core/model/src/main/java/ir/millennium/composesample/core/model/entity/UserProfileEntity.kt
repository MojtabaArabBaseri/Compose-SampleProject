package ir.millennium.composesample.core.model.entity

data class UserProfileEntity(
    val image: Int,
    val fullName: Int,
    val socialNetwork: List<UserProfileSocialNetworkEntity>
)