package dev.putra.chyruscore

import com.google.android.play.core.splitcompat.SplitCompatApplication
import dev.putra.chyruscore.di.injectApp
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class ChyrusApp : SplitCompatApplication() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@ChyrusApp)
            androidLogger()
            injectApp()
        }
    }

}