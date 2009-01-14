package concurrent

import fj.control.parallel.{Actor, Promise}
import fj.Function.curry
import fj.control.parallel.Strategy.executorStrategy
import fjs.control.parallel.Strategy.parMap
import fjs.control.parallel.Promise._
import fjs.control.parallel.Actor._
import Integer.parseInt
import List.range
import java.util.concurrent.Executors.newFixedThreadPool
import fjs.F._
import fjs.F2._
import fjs.P1._
import fjs.P2._
import fjs.data.List._
import fjs.control.parallel.Strategy.ListPar

/**
 * Parallel Fibonacci numbers.
 * Based on a Haskell example by Don Stewart.
 * Author: Runar
 */
object Fibs {
  val CUTOFF = 35;

  def main(args: Array[String]) = {
    if (args.length < 1)
      error("This program takes an argument: number_of_threads")

    val threads = parseInt(args(0))
    val pool = newFixedThreadPool(threads)
    implicit def s[A] = executorStrategy[A](pool)

    // This actor performs output and detects the termination condition.
    val out: Actor[List[Int]] = actor{
      ns =>
        for ((n, i) <- ns.zipWithIndex) printf("n=%d=>%d\n", i, n)
        pool.shutdown()
    }

    // A parallel recursive Fibonacci function
    def fib(n: Int): Promise[Int] = {
      if (n < CUTOFF) promise(() => seqFib(n))
      else fib(n - 1).bind(fib(n - 2), curry((_: Int) + (_: Int)))
    }

    println("Calculating Fibonacci sequence in parallel...")
    out ! sequence(parMap[Int, Promise[Int], List](fib, range(0, 46)));
  }

  // The sequential version of the recursive Fibonacci function
  def seqFib(n: Int): Int = if (n < 2) n else seqFib(n - 1) + seqFib(n - 2);
}