data class Sample(val x: Int, val y: Int, val z: Int, val result: Result?)
data class SampleDouble(val x: Double, val y: Double, val z: Double, val result: Result?)
enum class Result { A, B }