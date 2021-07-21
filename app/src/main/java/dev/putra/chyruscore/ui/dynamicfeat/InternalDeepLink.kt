package dev.putra.chyruscore.ui.dynamicfeat

object InternalDeepLink {
    const val DOMAIN = "myapp://"

    const val TODO = "${DOMAIN}todo"
    const val FAVORITE = "${DOMAIN}favorite"

    fun makeCustomDeepLink(id: String): String {
        return "${DOMAIN}customDeepLink?id=${id}"
    }

    //NAV FRAGMENT LABEL MENU
    const val TODO_LABEL = "TodoFragment"
    const val FAVORITE_LABEL = "FavoriteFragment"
}