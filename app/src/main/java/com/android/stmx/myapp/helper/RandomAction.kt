package com.android.stmx.myapp.helper

import com.android.stmx.myapp.domain.model.Action
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.ThreadLocalRandom
import kotlin.random.Random

object RandomAction {
    private val places = arrayListOf(
            "Школв 43",
            "Гимназия 51",
            "Гимназия 12",
            "Школв 59",
            "ДЮСШ",
            "ЗАЛ ТПУ",
    )
    private val sports = arrayListOf(
            "Баскетбол",
            "Волейбол",
            "Футбол",
            "Бег",
            "Танцы",
            "Занятия фитнесом",
            "Гандбол",
            "Лапта"
    )

    fun generateRandomAction(): Action {
        return Action.newInstance(
                Date(),
                generateRandomDate(),
                generateRandomDescription(),
                Random.nextInt(1, 10),
                Random.nextInt(150, 350),
                places.shuffled()[0],
                sports.shuffled()[0]
        )
    }

    private fun generateRandomDate(): Date {
        val d1 = SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.ROOT).parse("2020-12-09 00:00")
        val d2 = SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.ROOT).parse("2020-12-31 00:00")
        return Date(ThreadLocalRandom.current().nextLong(d1.time, d2.time))
    }

    private fun generateRandomDescription(): String {
        return "Description № ${Random.nextInt(0, 200)}"
    }

}