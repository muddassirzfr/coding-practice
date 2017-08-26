package com.lagostout.elementsofprogramminginterviews.graphs

/**
 * Problem 19.2.3 page 365
 */
class SetPointToBlackAndGetLargestBlackRegionSize(
        matrix: List<List<Boolean>>) {

    private var sizeOfLargestBlackRegion = 0
    private val graph: MutableMap<Point, MutableSet<Point>> =
            toGraph(matrix)
    private val pointToComponentMap = computeComponents(graph).flatMap {
        component -> component.map { it to component.toMutableSet() }
    }.toMap().toMutableMap()

    fun setToBlackAndGetLargestBlackRegionSize(point: Point): Int {
        if (graph.containsKey(point)) return sizeOfLargestBlackRegion

        val adjacentPoints = mutableSetOf(
                point.copy(row = point.row - 1),
                point.copy(column = point.column - 1),
                point.copy(row = point.row + 1),
                point.copy(column = point.column + 1))
        val adjacentBlackPoints = adjacentPoints.filter { graph.containsKey(it) }
        val component = mutableSetOf(point)
        pointToComponentMap[point] = component

        // Add point to graph
        graph[point] = adjacentBlackPoints.toMutableSet()
        adjacentBlackPoints.forEach {
            adjacentPoint ->
            graph[adjacentPoint]?.add(point)
        }

        // Merge components
        adjacentBlackPoints.fold(component) {
            acc, adjacentBlackPoint ->
            pointToComponentMap[adjacentBlackPoint]?.apply {
                acc.addAll(this)
            }
            acc
        }

        println("adjacentBlackPoints $adjacentBlackPoints")
        println()
        // Update components of adjacent points
        adjacentBlackPoints.forEach {
            pointToComponentMap[it]?.addAll(component)
        }

        if (component.size > sizeOfLargestBlackRegion)
            sizeOfLargestBlackRegion = component.size

        println("graph $graph")
        println()
        println("component $component")
        println()
        println("pointToComponentMap $pointToComponentMap")

        return sizeOfLargestBlackRegion
    }

}