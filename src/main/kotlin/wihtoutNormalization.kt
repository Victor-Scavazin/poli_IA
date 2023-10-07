fun test(
    k: List<Int>,
    ponderation: Boolean = false,
    samples: List<Sample>,
    sample9: Sample,
    onlyOdds: Boolean = false,
    linarNormalization: Boolean = false,
    zScoreNormalization: Boolean = false,
) {
    val newSamples: List<SampleDouble>
    val newSample : SampleDouble


    if (zScoreNormalization && linarNormalization) throw IllegalStateException("Choose only one Normalization")

    if (linarNormalization) {
        val linear = linearNormalization(samples.newSamples() + listOf(sample9).newSamples()).toMutableList()
        newSample = linear.removeLast()
        newSamples = linear
    }
    else if (zScoreNormalization) {
        val zScore = zScore(samples.newSamples() + listOf(sample9).newSamples()).toMutableList()
        newSample = zScore.removeLast()
        newSamples = zScore
    }
    else {
        newSample = listOf(sample9).newSamplesDouble().first()
        newSamples = samples.newSamplesDouble()
    }

    specialPrint()
    k.forEach {
        val result = knn(newSamples, newSample, it)
        if (onlyOdds && it !in listOf(1, 3, 5, 7)) return@forEach
        else println("k: $it,\nResult: ${result.first}\nClass chosen: ${result.second}\n-----------------------------------------------------------------------------------------------------------------------------------------")
    }

    specialPrint(true)

    k.forEach {
        val result = knn(newSamples, newSample, it, true)
        if (onlyOdds && it !in listOf(1, 3, 5, 7)) return@forEach
        else println("k: $it,\nResult: ${result.first}\nClass chosen: ${result.second}\n-----------------------------------------------------------------------------------------------------------------------------------------")
    }
}

fun specialPrint(ponderation: Boolean = false) {
    println()
    println("=========================================================================================================================================")
    println("============================================================= ${if (ponderation) "COM PONDERAÇÃO " else "SEM PONDERAÇÃO "}============================================================")
    println("=========================================================================================================================================")
    println()
}
private fun List<Sample>.newSamplesDouble(): List<SampleDouble> = this.map {
    SampleDouble(
        it.x.toDouble(),
        it.y.toDouble(),
        it.z.toDouble(),
        it.result
    )
}
private fun List<Sample>.newSamples(): List<Sample> = this.map {
    Sample(
        it.x,
        it.y,
        it.z,
        it.result
    )
}