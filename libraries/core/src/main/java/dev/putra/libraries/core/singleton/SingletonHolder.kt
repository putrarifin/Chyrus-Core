package dev.putra.libraries.core.singleton

/**
 * Created by Chyrus on 3/30/2020.
 */

// see https://blog.mindorks.com/how-to-create-a-singleton-class-in-kotlin
// and https://medium.com/@BladeCoder/kotlin-singletons-with-argument-194ef06edd9e
// Kotlin singletons really should have a constructor
open class SingletonHolder<out T : Any, in A>(creator: (A) -> T) {
    private var creator: ((A) -> T)? = creator
    @Volatile private var instance: T? = null

    fun getInstance(arg: A): T {
        val checkInstance = instance
        if (checkInstance != null) {
            return checkInstance
        }

        return synchronized(this) {
            val checkInstanceAgain = instance
            if (checkInstanceAgain != null) {
                checkInstanceAgain
            } else {
                val created = creator!!(arg)
                instance = created
                creator = null
                created
            }
        }
    }
}