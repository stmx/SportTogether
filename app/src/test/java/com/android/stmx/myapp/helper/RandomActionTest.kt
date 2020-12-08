package com.android.stmx.myapp.helper

import org.junit.Assert.*
import org.junit.Test

class RandomActionTest {
    @Test
    fun generatorRandom() {
        println(RandomAction.generateRandomAction().toString())
        println(RandomAction.generateRandomAction().toString())
        println(RandomAction.generateRandomAction().toString())
        println(RandomAction.generateRandomAction().toString())
    }
}