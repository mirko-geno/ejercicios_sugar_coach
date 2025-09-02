package com.example.learning.backend

enum class KindRace(val strength: Int) {
    Pelosos(1),
    Sureños(2),
    Enanos(3),
    Numenoreanos(4),
    Elfos(5)
}


enum class EvilRace(val strength: Int) {
    Sureños(2),
    Orcos(2),
    Goblins(2),
    Huargos(3),
    Trolls (5)
}


class Army<T>(val members: MutableMap<T, Int> = mutableMapOf()) {
    fun add(race: T, count: Int) {
        members[race] = members.getOrDefault(race, 0) + count
    }

    fun totalStrength(): Int {
        var total = 0
        for ((race, count) in members) {
            // dereference T type into Kind/Evil Race
            val strength = when(race) {
                is KindRace -> race.strength
                is EvilRace -> race.strength
                else -> 0
            }
            total += strength * count
        }
        return total
    }
}


fun battle(goodarmy: Army<KindRace>, evilarmy: Army<EvilRace>): String {
    val goodStrength = goodarmy.totalStrength()
    val evilStrength = evilarmy.totalStrength()

    return when {
        goodStrength > evilStrength -> "Gana el bien"
        evilStrength > goodStrength -> "Gana el mal"
        else -> "Empate"
    }
}
