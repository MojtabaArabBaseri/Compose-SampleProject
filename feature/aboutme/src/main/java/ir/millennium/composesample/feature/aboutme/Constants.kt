package ir.millennium.composesample.feature.aboutme

import ir.millennium.composesample.core.model.entity.UserProfileEntity
import ir.millennium.composesample.core.model.entity.UserProfileSocialNetworkEntity

object Constants {

    val USER_PROFILE_DATA = UserProfileEntity(
        image = R.drawable.image_user,
        fullName = R.string.full_name,
        socialNetwork = ArrayList<UserProfileSocialNetworkEntity>().apply {
            add(
                UserProfileSocialNetworkEntity(
                    title = R.string.github,
                    link = R.string.link_github
                )
            )
            add(
                UserProfileSocialNetworkEntity(
                    title = R.string.gitlab,
                    link = R.string.link_gitlab
                )
            )
            add(
                UserProfileSocialNetworkEntity(
                    title = R.string.linkedin,
                    link = R.string.link_linkedin
                )
            )
            add(
                UserProfileSocialNetworkEntity(
                    title = R.string.telegram,
                    link = R.string.link_telegram
                )
            )
            add(
                UserProfileSocialNetworkEntity(
                    title = R.string.whatsapp,
                    link = R.string.link_whatsapp
                )
            )
            add(
                UserProfileSocialNetworkEntity(
                    title = R.string.instagram,
                    link = R.string.link_instagram
                )
            )
        })
}