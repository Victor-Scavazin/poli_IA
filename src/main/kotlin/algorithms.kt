import kotlin.math.abs
fun knn(
    samples: List<Sample>,
    sample: Sample,
    k: Int,
    voteWeighting: Boolean = false
): Pair<Map<Result, Double>, Result?> {
    val sortedSamples = samples.sortedBy { manhattanDistance(it, sample) }
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

private fun manhattanDistance(P: Sample, Q: Sample): Double {
    val absoluteDistance = abs(P.x - Q.x) + abs(P.y - Q.y) + abs(P.z - Q.z)
    return absoluteDistance.toDouble()
}