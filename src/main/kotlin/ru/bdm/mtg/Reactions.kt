package ru.bdm.mtg

fun Card.addMana(color: Mana){
    me.mana.add(color)
}
fun RotateCard.play() {
    rotate()
    spendMana()
    move()
}

fun RotateCard.rotate(newRotate:Boolean = true){
    rotated = newRotate
}

fun Card.move(start: Kit<Card> = me.hand, end : Kit<Card> = me.battlefield)  {
    start.take(this)
    end.add(this)
}

fun Card.spendMana(player: StatePlayer = me){

    for(m in cost){
        player.mana[m.key]?.let {
            if (it > m.value)
                player.mana[m.key] = it - m.value
            else
                player.mana.remove(m.key)
        }
    }
    cost[Mana.NEUTRAL]?.let {
        var count = it
        for (m in player.mana){
            if(m.value > count){
                player.mana[m.key] = m.value - count
                break
            }
            if (m.value == count){
                player.mana.remove(m.key)
                break
            }
            if (m.value < count){
                count -= m.value
                player.mana.remove(m.key)
            }
        }
    }
}