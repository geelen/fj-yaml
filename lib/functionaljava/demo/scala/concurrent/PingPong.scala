package concurrent

import fj.control.parallel.Actor
import fjs.control.parallel.Actor._
import java.text.MessageFormat;
import java.util.concurrent.Executors.newFixedThreadPool;
import fj.control.parallel.Strategy;
import fj.control.parallel.Strategy.executorStrategy;
import java.lang.Integer.parseInt

class Pong(implicit s: Strategy[fj.Unit]) {
  val pong: Actor[Ping] = actor(_ ! this)
}

class Ping(i: Int, pong: Actor[Ping], callback: Actor[Ping])(implicit s: Strategy[fj.Unit]) {
  private var n: Int = i

  val ping = actor{
    m: Actor[Ping] =>
            n = n - 1                  
            (if (n > 0) m else callback) ! this
  }

  // Commence pinging
  def start = pong ! this

  // Receive a pong
  def !(pong: Pong) = ping ! pong.pong
}

object PingPong {
  def start(actors: Int, pings: Int, callback: Actor[Ping])(implicit s: Strategy[fj.Unit]) = {
    // We will use one Pong actor...
    val pong = new Pong().pong

    // ...and an awful lot of Ping actors.
    (1 to actors) foreach {
      i => { new Ping(pings, pong, callback).start
             if (actors < 10 || i % (actors / 10) == 0)
               printf("%d actors started.\n", i) }
    }
  }

  def main(args: Array[String]): Unit = {
    if (args.length < 3)
      error("This program takes three arguments: number_of_actors, pings_per_actor, number_of_threads")

    val actors = parseInt(args(0))
    val pings = parseInt(args(1))
    val threads = parseInt(args(2))

    val pool = newFixedThreadPool(threads)
    implicit def s[A] = executorStrategy[A](pool)

    var done = 0

    // This actor gives feedback to the user that work is being done
    // and also terminates the program when all work has been completed.
    val callback = queueActor{
      p: Ping =>
              done = done + 1
              if (done >= actors) {
                println("All done.")
                pool.shutdown
              } else if (actors < 10 || done % (actors / 10) == 0)
                printf("%d actors done (%d total pongs).\n", done, pings * done)
    }.asActor

    start(actors, pings, callback)
  }
}
