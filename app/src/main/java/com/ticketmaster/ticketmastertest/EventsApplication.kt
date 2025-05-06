package com.ticketmaster.ticketmastertest

import android.app.Application
import org.koin.core.context.startKoin
import org.koin.ksp.generated.defaultModule

class EventsApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            modules(defaultModule)
        }
    }
}
