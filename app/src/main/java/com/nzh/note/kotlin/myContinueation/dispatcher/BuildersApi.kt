package com.nzh.note.kotlin.myContinueation.dispatcher


object MyDispatchers {

    val Default by lazy {
        DispatcherImpl(DefaultDispatcher)
    }

    val UI by lazy {
        DispatcherImpl(UIDispatcher)
    }

}