package greedy

import kotlin.collections.*

private val states: MutableSet<String> = mutableSetOf()
private var stations: MutableMap<String, Set<String>> = mutableMapOf()

fun main() {
    init()
    println(greedy(states, stations))
}

private fun greedy(states: Set<String>, stations: Map<String, Set<String>>): Set<String> {
    val result = mutableSetOf<String>()
    var statesNeeded = LinkedHashSet(states)
    while (statesNeeded.isNotEmpty()) {
        var bestStations = ""
        val statesCovered = LinkedHashSet<String>()
        for ((stationName, stationStates) in stations) {

            val covered = statesNeeded.intersect(stationStates)
            if (covered.size > statesCovered.size) {
                statesCovered.clear()
                covered.forEach { statesCovered.add(it) }
                bestStations = stationName
            }
        }

        statesNeeded = statesNeeded.subtract(statesCovered) as LinkedHashSet<String>
        result.add(bestStations)
    }
    return result
}

private fun init() {
    states.add("mt")
    states.add("wa")
    states.add("or")
    states.add("id")
    states.add("nv")
    states.add("ut")
    states.add("ca")
    states.add("az")
    states.add("lo")
    states.add("mn")

    stations["kone"] = mutableSetOf("id", "nv", "ut")
    stations["ktwo"] = mutableSetOf("wa", "id", "mt")
    stations["kthree"] = mutableSetOf("or", "nv", "ca")
    stations["kfour"] = mutableSetOf("nv", "ut")
    stations["kfive"] = mutableSetOf("ca", "az")
    stations["ksix"] = mutableSetOf("lo", "az")
    stations["kseven"] = mutableSetOf("mn", "lo")
}