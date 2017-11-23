package server.domain.models

import org.jetbrains.exposed.dao.EntityID
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntIdTable

object Lotteries: IntIdTable() {
    val price = integer("price")
    val draw = date("draw")
}

class Lottery(id: EntityID<Int>) : IntEntity(id) {

    var price by Lotteries.price
    var draw by Lotteries.draw

    init {
        require(price > 0)
        require(draw.isAfterNow)
    }
}