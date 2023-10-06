import kotlin.math.abs
import kotlin.collections.get as let

data class Sample(val x: Int, val y: Int, val z: Int,val result : Result?)
enum class Result{A,B}

fun main(args: Array<String>) {

val sample1 = Sample(0,250,36,Result.A)
val sample2 = Sample(10,150,34,Result.B)
val sample3 = Sample(2,90,10,Result.A)
val sample4 = Sample(6,78,8,Result.B)
val sample5 = Sample(4,20,1,Result.A)
val sample6 = Sample(1,170,70,Result.B)
val sample7 = Sample(8,160,41,Result.A)
val sample8 = Sample(10,180,38,Result.B)
val sample9 = Sample(6,200,45,null)
val k : List<Int> = listOf(1,2,3,4,5)
val samples = listOf(sample1,sample2,sample3,sample4,sample5,sample6,sample7,sample8)

    k.forEach{
        val result = knn(samples, sample9, it)
        println("k: $it,\nResult: $result\n==========================================================================================================================================")
    }
}

fun knn(samples: List<Sample>, sample: Sample, k: Int):  Pair<Map<Result, Int>, Result?> {
    val sortedSamples = samples.sortedBy {  manhattanDistance(it, sample) }
    val kNearestSamples = sortedSamples.subList(0, k)
    val result = kNearestSamples.groupingBy { it.result!! }.eachCount()

    return if (result[Result.A] == result[Result.B]) result to null // Considering no voting ponderation
    else result to  result.maxBy { it.value }.key
}

fun manhattanDistance(P: Sample, Q: Sample): Int {
    return abs(P.x - Q.x) + abs(P.y - Q.y) + abs(P.z - Q.z)
}