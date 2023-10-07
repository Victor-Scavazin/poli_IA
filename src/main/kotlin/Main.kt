fun main() {

    val sample1 = Sample(0, 250, 36, Result.A)
    val sample2 = Sample(10, 150, 34, Result.B)
    val sample3 = Sample(2, 90, 10, Result.A)
    val sample4 = Sample(6, 78, 8, Result.B)
    val sample5 = Sample(4, 20, 1, Result.A)
    val sample6 = Sample(1, 170, 70, Result.B)
    val sample7 = Sample(8, 160, 41, Result.A)
    val sample8 = Sample(10, 180, 38, Result.B)
    val sample9 = Sample(6, 200, 45, null)
    //val k: List<Int> = listOf(1, 2, 3, 4, 5)
    val k: List<Int> = listOf(1, 2, 3, 4, 5, 6, 7, 8)
    val samples = listOf(sample1, sample2, sample3, sample4, sample5, sample6, sample7, sample8)

    test(k, false, samples, sample9, false)
    test(k, true, samples, sample9, false)

    test(k, false, samples, sample9, false, linarNormalization = true)
    test(k, true, samples, sample9, false, linarNormalization = true)

    test(k, false, samples, sample9, false, zScoreNormalization = true)
    test(k, true, samples, sample9, false, zScoreNormalization = true)

}





