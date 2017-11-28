package server.domain.models

import org.jetbrains.exposed.dao.EntityID
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.IntIdTable
import org.joda.time.DateTime

object Lotteries: IntIdTable() {
    val award = integer("award")
    val price = integer("price")
    val draw = datetime("draw")
}

data class Lottery(var id: Int?, var award: Int, var price: Int, var draw: DateTime) {
    init {
        require(price > 0)
        require(award > 0)

    }
}

class LotteryEntity(id: EntityID<Int>) : IntEntity(id) {

    companion object : IntEntityClass<LotteryEntity>(Lotteries)

    var award by Lotteries.award
    var price by Lotteries.price
    var draw by Lotteries.draw

    fun asLottery(): Lottery = Lottery(id.value, award, price, draw)
}