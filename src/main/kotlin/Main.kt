import kotlin.math.abs
import kotlin.math.max

data class Sample(val x: Int, val y: Int, val z: Int, val result: Result?)
enum class Result { A, B }

fun main(args: Array<String>) {

    val sample1 = Sample(0, 250, 36, Result.A)
    val sample2 = Sample(10, 150, 34, Result.B)
    val sample3 = Sample(2, 90, 10, Result.A)
    val sample4 = Sample(6, 78, 8, Result.B)
    val sample5 = Sample(4, 20, 1, Result.A)
    val sample6 = Sample(1, 170, 70, Result.B)
    val sample7 = Sample(8, 160, 41, Result.A)
    val sample8 = Sample(10, 180, 38, Result.B)
    val sample9 = Sample(6, 200, 45, null)
    val k: List<Int> = listOf(1, 2, 3, 4, 5)
    val samples = listOf(sample1, sample2, sample3, sample4, sample5, sample6, sample7, sample8)

    println("=========================================================================================================================================")
    println("============================================================= SEM PONDERAÇÃO ============================================================")
    println("=========================================================================================================================================")
    println()
    k.forEach {
        val result = knn(samples, sample9, it)
        if (it in listOf(1,3,5))   println("k: $it,\nResult: ${result.first}\nClass chosen: ${result.second}\n-----------------------------------------------------------------------------------------------------------------------------------------")
    }
    println()
    println("=========================================================================================================================================")
    println("============================================================= COM PONDERAÇÃO ============================================================")
    println("=========================================================================================================================================")
    println()

    k.forEach {
        val result = knn(samples, sample9, it,true)
        if (it in listOf(1,3,5)) println("k: $it,\nResult: ${result.first}\nClass chosen: ${result.second}\n-----------------------------------------------------------------------------------------------------------------------------------------")
    }
}

fun knn(samples: List<Sample>, sample: Sample, k: Int, voteWeighting:Boolean=false):  Pair<Map<Result, Double>, Result?> {
    val sortedSamples = samples.sortedBy {  manhattanDistance(it, sample) }
    val kNearestSamples = sortedSamples.subList(0, k)

    val result = kNearestSamples.groupingBy { it.result!! }.eachCount()

    var weightsA = 0.0
    var weightsB = 0.0
    val resultMap = mutableMapOf<Result, Double>()

    val resultWeighting = kNearestSamples.forEach {
        val weight = 1.0 / manhattanDistance(it, sample)
        if (it.result == Result.A) weightsA += weight else weightsB += weight
    }.let {
        resultMap[Result.A] = weightsA
        resultMap[Result.B] = weightsB
        if (weightsA > weightsB) Result.A else Result.B

    }



    return if (result[Result.A] == result[Result.B] || voteWeighting) resultMap to resultWeighting
    else result.mapValues { it.value.toDouble() } to result.maxBy { it.value }.key
}

fun manhattanDistance(P: Sample, Q: Sample): Double {
    val absoluteDistance = abs(P.x - Q.x) + abs(P.y - Q.y) + abs(P.z - Q.z)
    return absoluteDistance.toDouble()
}