import kotlin.random.Random

fun test(
    k: List<Int>,
    ponderation: Boolean = false,
    samples: List<Sample>,
    sample9: Sample,
    onlyOdds: Boolean = false,
    linearNormalization: Boolean = false,
    zScoreNormalization: Boolean = false,
    validation: Boolean = false
) {

    val newSamples: List<SampleDouble>
    val newSample: SampleDouble

    var samplesLOO: List<Sample> = listOf()
    var validateLOO: Sample? = null

    if (validation) {
        val randomInt = Random.nextInt(0, samples.size)
        val newSamplesValid = samples.newSamples().toMutableList()

        validateLOO = newSamplesValid[randomInt]
        newSamplesValid.removeAt(randomInt)
        samplesLOO = newSamplesValid
    }


    if (zScoreNormalization && linearNormalization) throw IllegalStateException("Choose only one Normalization")

    if (linearNormalization) {
        val linear = linearNormalization(samples.newSamples() + listOf(sample9).newSamples()).toMutableList()
        newSample = linear.removeLast()
        newSamples = linear
    } else if (zScoreNormalization) {
        val zScore = zScore(samples.newSamples() + listOf(sample9).newSamples()).toMutableList()
        newSample = zScore.removeLast()
        newSamples = zScore
    } else {
        newSample = listOf(sample9).newSamplesDouble().first()
        newSamples = samples.newSamplesDouble()
    }

    specialPrint()
    k.forEach {
        val result = knn(
            samplesLOO.newSamplesDouble().let { ns -> ns.ifEmpty { newSamples } },
            validateLOO?.toSampleDouble() ?: newSample,
            it
        )
        if (onlyOdds && it !in listOf(1, 3, 5, 7)) return@forEach
        else println("k: $it,\nResult: ${result.first}\nClass chosen: ${result.second}\n-----------------------------------------------------------------------------------------------------------------------------------------")
        //if (validation) println(" original: ${samples.result}
    }

    specialPrint(true)

    k.forEach {
        val result = knn(
            samplesLOO.newSamplesDouble().let { ns -> ns.ifEmpty { newSamples } },
            validateLOO?.toSampleDouble() ?: newSample,
            it, true
        )
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

fun List<Sample>.newSamples(): List<Sample> = this.map {
    Sample(
        it.x,
        it.y,
        it.z,
        it.result
    )
}

fun Sample.toSampleDouble(): SampleDouble = SampleDouble(
    this.x.toDouble(),
    this.y.toDouble(),
    this.z.toDouble(),
    this.result
)
