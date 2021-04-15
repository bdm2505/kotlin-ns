package ru.bdm.mtg.cards

import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotlinx.serialization.modules.SerializersModule
import ru.bdm.mtg.AbstractCard
import ru.bdm.mtg.BattleState
import ru.bdm.mtg.Card
import ru.bdm.mtg.RotateCard
import ru.bdm.mtg.cards.chips.BlackDaemon
import ru.bdm.mtg.cards.chips.BlueIllusion
import ru.bdm.mtg.cards.chips.WhiteHumanWarrior
import ru.bdm.mtg.cards.creatures.*
import ru.bdm.mtg.cards.lands.*
import ru.bdm.mtg.cards.spells.AlchemistsGift


object CardSerializer {
    private val module = SerializersModule {
        polymorphic(AbstractCard::class, Card::class, Card.serializer())
        polymorphic(AbstractCard::class, Land::class, Land.serializer())
        polymorphic(AbstractCard::class, RotateCard::class, RotateCard.serializer())
        polymorphic(AbstractCard::class, Creature::class, Creature.serializer())

        polymorphic(AbstractCard::class, BlackDaemon::class, BlackDaemon.serializer())
        polymorphic(AbstractCard::class, BlueIllusion::class, BlueIllusion.serializer())
        polymorphic(AbstractCard::class, WhiteHumanWarrior::class, WhiteHumanWarrior.serializer())
        polymorphic(AbstractCard::class, AirElemental::class, AirElemental.serializer())
        polymorphic(AbstractCard::class, AlpineWatchdog::class, AlpineWatchdog.serializer())
        polymorphic(AbstractCard::class, ArchfiendsVessel::class, ArchfiendsVessel.serializer())
        polymorphic(AbstractCard::class, GiantKiller::class, GiantKiller.serializer())
        polymorphic(AbstractCard::class, LuminarchAspirant::class, LuminarchAspirant.serializer())
        polymorphic(AbstractCard::class, SeasonedHallowblade::class, SeasonedHallowblade.serializer())
        polymorphic(AbstractCard::class, SkyclaveApparition::class, SkyclaveApparition.serializer())
        polymorphic(AbstractCard::class, UsherOfTheFallen::class, UsherOfTheFallen.serializer())

        polymorphic(AbstractCard::class, BloodfellCaves::class, BloodfellCaves.serializer())
        polymorphic(AbstractCard::class, BlossomingSands::class, BlossomingSands.serializer())
        polymorphic(AbstractCard::class, DismalBackwater::class, DismalBackwater.serializer())
        polymorphic(AbstractCard::class, Forest::class, Forest.serializer())
        polymorphic(AbstractCard::class, Island::class, Island.serializer())
        polymorphic(AbstractCard::class, JungleHollow::class, JungleHollow.serializer())
        polymorphic(AbstractCard::class, Mountain::class, Mountain.serializer())
        polymorphic(AbstractCard::class, Plains::class, Plains.serializer())
        polymorphic(AbstractCard::class, RadiantFountain::class, RadiantFountain.serializer())
        polymorphic(AbstractCard::class, ScouredBarrens::class, ScouredBarrens.serializer())
        polymorphic(AbstractCard::class, Swamp::class, Swamp.serializer())
        polymorphic(AbstractCard::class, SwiftwaterCliffs::class, SwiftwaterCliffs.serializer())

        polymorphic(AbstractCard::class, AlchemistsGift::class, AlchemistsGift.serializer())

    }

    private val format = Json {
        serializersModule = module
        encodeDefaults = true
    }

    fun encode(p1: BattleState): String =
        format.encodeToString(p1)


    fun decode(p1: String): BattleState =
        format.decodeFromString(p1)


}



