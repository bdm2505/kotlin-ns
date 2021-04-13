package ru.bdm.mtg.cards.creatures

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import ru.bdm.mtg.Executor
import ru.bdm.mtg.Tag
import ru.bdm.mtg.toMana

interface GiantKillerInterface : CreatureInterface {

    fun getRotateTargets(): Set<Int> = (me.battlefield - creature.id + enemy.battlefield)

    fun canRotateTarget() =
        isActivePhase() && inBattlefield() && enoughMana(GiantKillerCosts.rotate) && !getRotateTargets().isEmpty()

    fun rotateTargetActions(): List<() -> Unit> = getRotateTargets().map {
        {
            rotate()
            spendMana(GiantKillerCosts.rotate)
            state.find<Creature>(it)?.apply { rotated = !rotated } ?: System.err.println("not find creature $it")
        }
    }

    fun getDestroyTargets() =
        (me.battlefield + enemy.battlefield).filter { state.find<Creature>(it)?.run { force >= 4 } ?: false }

    fun canDestroyTarget() =
        isActivePhase() && inHand() && enoughMana(GiantKillerCosts.chop) && !getDestroyTargets().isEmpty()

    fun destroyTargetActions() = getDestroyTargets().map {
        {
            if (enemy.battlefield.contains(it))
                move(enemy.battlefield, enemy.graveyard, it)
            else if (me.battlefield.contains(it))
                move(me.battlefield, me.graveyard, it)
            move(me.hand, me.exile)
            spendMana(GiantKillerCosts.chop)
        }
    }

    fun canPlayFromExile() = isActivePhase() && enoughMana() && inExile()
    fun playFromExile() = listOf {
        move(me.exile, me.battlefield)
        spendMana()
        creature.isWentOnBattlefield = true
    }
}

class GiantKillerExecutor : CreatureExecutor(), GiantKillerInterface {
    init {
        any(this::canRotateTarget) {
            rotateTargetActions()
        }
        any(this::canDestroyTarget) {
            destroyTargetActions()
        }
        any(this::canPlayFromExile) {
            playFromExile()
        }
    }
}

@Serializable
@SerialName("GiantKiller")
class GiantKiller() : Creature(1, 2) {

    init {
        cost = "W".toMana()
        tag(Tag.HUMAN, Tag.PEASANT, Tag.ADVENTURE, Tag.INSTANT)
    }

    override fun executor(): Executor = GiantKillerExecutor()
}

object GiantKillerCosts {
    val rotate = "CW".toMana()
    val chop = "CCW".toMana()
}