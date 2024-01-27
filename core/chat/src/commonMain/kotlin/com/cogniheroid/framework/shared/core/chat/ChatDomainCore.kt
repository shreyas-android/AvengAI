package com.cogniheroid.framework.shared.core.chat

internal object ChatDomainCore {

    lateinit var instance: Instance
    fun init(databaseDriverFactory: DatabaseDriverFactory){
        instance = Instance(databaseDriverFactory.createDriver())
    }
}