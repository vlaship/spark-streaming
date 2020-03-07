package vlaship.spark.streaming

import org.apache.spark.SparkConf
import org.apache.spark.storage.StorageLevel
import org.apache.spark.streaming.{Seconds, StreamingContext}

object App {
  def main(args: Array[String]): Unit = {

    val conf = new SparkConf().setMaster("local").setAppName("NetworkWordCount")
    val context = new StreamingContext(conf, Seconds(1))

    context
      .socketTextStream("localhost", 8888, StorageLevel.NONE)
      .flatMap(_.split(' '))
      .map(w => (w, 1))
      .reduceByKey(_ + _)
      .print()

    context.start()
    context.awaitTermination()
  }

}
