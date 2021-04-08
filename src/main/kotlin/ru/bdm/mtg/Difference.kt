package ru.bdm.mtg

data class Difference(
    val manaAdd: List<Mana>,
    val manaRemoved: List<Mana>,
    val handAdd: List<Card>,
    val landsAdd: List<Card>,
    val deckAdd: List<Card>,
    val graveyardAdd: List<Card>,
    val handRemoved: List<Card>,
    val landsRemoved: List<Card>,
    val deckRemoved: List<Card>,
    val graveyardRemoved: List<Card>,
    val numberCourse: Int,
    val phase: List<Phase>,
    val hp: Int,
    val name: String,
    val isLandPlayable: Boolean
) {
    override fun toString(): String {

        return "$hp❤ (" +
                (if (phase.isEmpty()) "" else "$phase,") +
                (if (handAdd.isEmpty()) "" else "handAdd=$handAdd,") +
                (if (landsAdd.isEmpty()) "" else " landsAdd=$landsAdd,") +
                (if (deckAdd.isEmpty()) "" else " deckAdd=$deckAdd,") +
                (if (graveyardAdd.isEmpty()) "" else " graveyardAdd=$graveyardAdd,") +
                (if (handRemoved.isEmpty()) "" else " handRemoved=$handRemoved,") +
                (if (landsRemoved.isEmpty()) "" else " landsRemoved=$landsRemoved,") +
                (if (deckRemoved.isEmpty()) "" else " deckRemoved=$deckRemoved,") +
                (if (graveyardRemoved.isEmpty()) "" else " graveyardRemoved=$graveyardRemoved,") +
                " numberCourse=$numberCourse" +
                " isLandPlayable=$isLandPlayable" +
                "  $name) "
    }

}