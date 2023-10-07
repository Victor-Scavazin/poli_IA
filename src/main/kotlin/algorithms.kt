import kotlin.math.abs
import kotlin.math.pow
import kotlin.math.sqrt

fun knn(
    samples: List<SampleDouble>,
    sample: SampleDouble,
    k: Int,
    voteWeighting: Boolean = false,
    p : Int=1
): Pair<Map<Result, Double>, Result?> {
    val sortedSamples = samples.sortedBy { manhattanDistance(it, sample) }
    val kNearestSamples = sortedSamples.subList(0, k)

    val result = kNearestSamples.groupingBy { it.result!! }.eachCount()

    var weightsA = 0.0
    var weightsB = 0.0
    val resultMap = mutableMapOf<Result, Double>()
    val resultWeighting = kNearestSamples.forEach {
        val weight = (1.0 / manhattanDistance(it, sample)).pow(p)
        if (it.result == Result.A) weightsA += weight else weightsB += weight
    }.let {
        resultMap[Result.A] = weightsA
        resultMap[Result.B] = weightsB
        if (weightsA > weightsB) Result.A else Result.B
    }

    return if (result[Result.A] == result[Result.B] || voteWeighting) resultMap to resultWeighting
    else result.mapValues { it.value.toDouble() } to result.maxBy { it.value }.key
}

 fun linearNormalization(
    samples: List<Sample>,
): List<SampleDouble> {
    val samplesNormalized = mutableListOf<SampleDouble>()

    val xMax = samples.maxBy { it.x }.x
    val yMax = samples.maxBy { it.y }.y
    val zMax = samples.maxBy { it.z }.z
    val xMin = samples.minBy { it.x }.x
    val yMin = samples.minBy { it.y }.y
    val zMin = samples.minBy { it.z }.z
    samples.forEach {
        samplesNormalized.add(
            SampleDouble(
                (it.x - xMin).toDouble() / (xMax - xMin).toDouble(),
                (it.y - yMin).toDouble() / (yMax - yMin).toDouble(),
                (it.z - zMin).toDouble() / (zMax - zMin).toDouble(),
                it.result
            )
        )
    }
    return samplesNormalized
}

 fun zScore(
    samples: List<Sample>,
): List<SampleDouble> {
    val samplesNormalized = mutableListOf<SampleDouble>()
    val xMean = samples.map { it.x }.average()
    val yMean = samples.map { it.y }.average()
    val zMean = samples.map { it.z }.average()
    val xStd = samples.map { it.x }.standardDeviation()
    val yStd = samples.map { it.y }.standardDeviation()
    val zStd = samples.map { it.z }.standardDeviation()
    samples.forEach {
        samplesNormalized.add(
            SampleDouble(
                (it.x - xMean) / xStd,
                (it.y - yMean) / yStd,
                (it.z - zMean) / zStd,
                it.result
            )
        )
    }
    return samplesNormalized
}

fun List<Int>.standardDeviation(): Double {
    return this.let {
        val mean = it.average()
        val sum = it.sumOf { x -> (x - mean).pow(2.0) }
        sqrt(sum / it.size)
    }
}

private fun manhattanDistance(P: SampleDouble, Q: SampleDouble): Double {
    val absoluteDistance = abs(P.x - Q.x) + abs(P.y - Q.y) + abs(P.z - Q.z)
    return absoluteDistance.toDouble()
}