package ru.bdm.mtg.cards

import kotlinx.serialization.SerializationStrategy
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encodeToString
import kotlinx.serialization.encoding.Encoder
import kotlinx.serialization.json.Json
import kotlinx.serialization.modules.SerializersModule
import ru.bdm.mtg.*


object CardSerializer {
    private val module = SerializersModule {
        polymorphic(AbstractCard::class, Card::class, Card.serializer())
        polymorphic(AbstractCard::class, Land::class, Land.serializer())
        polymorphic(AbstractCard::class, RotateCard::class, RotateCard.serializer())
        polymorphic(AbstractCard::class, Creature::class, Creature.serializer())

    }

    private val format = Json {
        serializersModule = module
        encodeDefaults = true
    }

    fun encode(p1: State): String =
        format.encodeToString(p1)


    fun decode(p1: String): State =
        format.decodeFromString(p1)


}

