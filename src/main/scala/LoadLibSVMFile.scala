import org.apache.spark.{SparkConf, SparkContext}

object LoadLibSVMFile {
  import org.apache.spark.mllib.regression.LabeledPoint
  import org.apache.spark.mllib.util.MLUtils
  import org.apache.spark.rdd.RDD

  val conf = new SparkConf().setAppName("LogisticRegressionMail").setMaster("local")

  val sc = new SparkContext(conf)
  val examples: RDD[LabeledPoint] = MLUtils.loadLibSVMFile(sc, "data/mllib/sample_libsvm_data.txt")

  println(examples.first)
}
