fun withoutNormalization(
    k:List<Int>,
    ponderation:Boolean=false,
    samples:List<Sample>,
    sample9:Sample,
    onlyOdds:Boolean=false
    ){
    specialPrint()
    k.forEach {
        val result = knn(samples, sample9, it)
        if (onlyOdds && it !in listOf(1,3,5,7)) return@forEach
        else println("k: $it,\nResult: ${result.first}\nClass chosen: ${result.second}\n-----------------------------------------------------------------------------------------------------------------------------------------")
    }

    specialPrint(true)

    k.forEach {
        val result = knn(samples, sample9, it, true)
        if (onlyOdds && it !in listOf(1,3,5,7)) return@forEach
        else println("k: $it,\nResult: ${result.first}\nClass chosen: ${result.second}\n-----------------------------------------------------------------------------------------------------------------------------------------")
    }
}
fun specialPrint(ponderation: Boolean=false){
    println()
    println("=========================================================================================================================================")
    println("============================================================= ${if(ponderation) "COM PONDERAÇÃO " else "SEM PONDERAÇÃO " }============================================================")
    println("=========================================================================================================================================")
    println()
}