package ru.bdm.mtg

data class Difference(
    val mana: String?,
    val changes: List<Pair<AbstractCard, String>>,
    val hp: Int?,
    val name: String,
    val numberCourse: Int?,
    val isLandPlayable: Boolean?,
) {
    override fun toString(): String {

        return "${hp?.let { "${it}hp" } ?: ""}(" +
                " $name '${mana ?: ""}')" +
                " $changes" +
                (numberCourse?.let { " numberCourse=$it," } ?: "") +
                (isLandPlayable?.let { " isLandPlayable=$it" } ?: "")
    }

}