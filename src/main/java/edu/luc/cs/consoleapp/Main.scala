package edu.luc.cs.consoleapp

import org.apache.commons.collections4.queue.CircularFifoQueue
import scala.io.StdIn
import scala.util.Try

object Main {

  private val LastNWords = 10

  def main(args: Array[String]): Unit = {
    // Perform argument validity checking
    if (args.length > 1) {
      System.err.println("usage: ./target/universal/stage/bin/consoleapp [ last_n_words ]")
      System.exit(2)
    }

    val lastNWords = args.headOption.flatMap(arg => Try(arg.toInt).toOption)
      .filter(_ > 0)
      .getOrElse(LastNWords)

    val queue = new CircularFifoQueue[String](lastNWords)

    // Read words from standard input
    val inputStream = scala.io.Source.stdin.getLines()
    inputStream.foreach { line =>
      line.split("\\W+").foreach { word =>
        queue.add(word) // the oldest item automatically gets evicted
        println(queue)
      }
    }
  }
}
