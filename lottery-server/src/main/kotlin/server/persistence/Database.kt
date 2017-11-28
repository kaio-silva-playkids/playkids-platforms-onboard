package server.persistence

import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction
import org.joda.time.DateTime
import server.domain.models.*
import server.security.Hash

open class Database(private var configuration: Configuration) {

    fun start(): Database {

        val db = Database.connect(configuration.url(), configuration.driver, configuration.user, configuration.password)

        transaction {
            SchemaUtils.drop(Users, Lotteries, Tickets)
            SchemaUtils.create(Users, Lotteries, Tickets)

            // TODO put on DML config
            //DML
            LotteryEntity.new {
                award = 16;
                price = 2;
                draw = DateTime.now().plusDays(3)
            }
            LotteryEntity.new {
                award = 256;
                price = 16;
                draw = DateTime.now().plusDays(5)
            }
            LotteryEntity.new {
                award = 512;
                price = 32;
                draw = DateTime.now().plusDays(7)
            }
            LotteryEntity.new {
                award = 1024;
                price = 32;
                draw = DateTime.now().plusDays(11)
            }
            val l = LotteryEntity.new {
                award = 128;
                price = 8;
                draw = DateTime.now().plusDays(13)
            }

            val u = UserEntity.new {
                username= "user";
                email= "user@email.com";
                credit= 50;
                password= Hash.sha512("user").toLowerCase()
            }

            TicketEntity.new {
                user = u;
                lottery = l;
            }
        }

        return db
    }

}