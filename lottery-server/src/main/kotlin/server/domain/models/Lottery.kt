package server.domain.models

import org.jetbrains.exposed.dao.EntityID
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.IntIdTable
import org.joda.time.DateTime

object Lotteries: IntIdTable() {
    val price = integer("price")
    val draw = datetime("draw")
}

data class Lottery(var id: Int?, var price: Int, var draw: DateTime) {
    init {
        require(price > 0)
    }
}

class LotteryEntity(id: EntityID<Int>) : IntEntity(id) {

    companion object : IntEntityClass<LotteryEntity>(Lotteries)

    var price by Lotteries.price
    var draw by Lotteries.draw

    fun asLottery(): Lottery = Lottery(id.value, price, draw)
}