package playkids.onboard.server.persistence

import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction
import org.joda.time.DateTime
import playkids.onboard.server.models.*
import playkids.onboard.server.security.Hash

open class Database(private var configuration: playkids.onboard.server.persistence.Configuration) {

    fun start(): Database {

        val db = Database.connect(configuration.url(), configuration.driver, configuration.user, configuration.password)

        transaction {
            SchemaUtils.drop(Users, Lotteries, Tickets)
            SchemaUtils.create(Users, Lotteries, Tickets)

            // TODO put on DML config
            //DML
            val l1 = LotteryEntity.new {
                award = 16;
                price = 2;
                draw = DateTime.now().plusMinutes(1)
            }
            val l2 = LotteryEntity.new {
                award = 256;
                price = 16;
                draw = DateTime.now().plusMinutes(2)
            }
            val l3 = LotteryEntity.new {
                award = 512;
                price = 32;
                draw = DateTime.now().plusMinutes(3)
            }
            val l4 = LotteryEntity.new {
                award = 1024;
                price = 32;
                draw = DateTime.now().plusMinutes(4)
            }
            val l5 = LotteryEntity.new {
                award = 128;
                price = 8;
                draw = DateTime.now().plusMinutes(5)
            }

            val u1 = UserEntity.new {
                username= "user1";
                email= "user1@email.com";
                credit= 50;
                password= Hash.sha512("user").toLowerCase()
            }

            val u2 = UserEntity.new {
                username= "user2";
                email= "user2@email.com";
                credit= 50;
                password= Hash.sha512("user").toLowerCase()
            }

            TicketEntity.new {
                user = u1;
                lottery = l1;
            }

            TicketEntity.new {
                user = u1;
                lottery = l2;
            }

            TicketEntity.new {
                user = u1;
                lottery = l3;
            }

            TicketEntity.new {
                user = u2;
                lottery = l1;
            }

            TicketEntity.new {
                user = u2;
                lottery = l2;
            }

            TicketEntity.new {
                user = u2;
                lottery = l3;
            }

        }

        return db
    }

}