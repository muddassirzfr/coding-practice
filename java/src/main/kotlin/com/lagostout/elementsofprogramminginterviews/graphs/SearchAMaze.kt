package com.lagostout.elementsofprogramminginterviews.graphs

import java.util.*

/**
 * Problem 19.1 page 360
 */
object SearchMaze {

    data class Result(val path: List<Point<Boolean>>, val graph: Map<Point<Boolean>, Set<Point<Boolean>>>)
    data class Frame(val point: Point<Boolean>, val adjacentPoints: Iterator<Point<Boolean>>)

    fun findPathThroughMaze(
            grid: List<List<Boolean>>,
            entry: Point<Boolean>, exit: Point<Boolean>): Result {
        if (!containsOpenPixel(grid, entry) ||
                !containsOpenPixel(grid, exit))
            return Result(emptyList(), emptyMap())
        val graph = toGraph(grid)
        val stackOfFrames = LinkedList<Frame>()
        val pixelsInPath = mutableSetOf<Point<Boolean>>()
        stackOfFrames.push(
                Frame(point = entry,
                        adjacentPoints = graph[entry]!!.iterator()))
        pixelsInPath.add(entry)
        while (stackOfFrames.isNotEmpty() &&
                !(stackOfFrames.first().point == exit &&
                        stackOfFrames.last().point == entry)) {
            val (_, adjacentPoints) = stackOfFrames.peek()
            if (adjacentPoints.hasNext()) {
                val adjacentPoint = adjacentPoints.next()
                if (pixelsInPath.contains(adjacentPoint)) continue
                pixelsInPath.add(adjacentPoint)
                stackOfFrames.push(
                        Frame(point = adjacentPoint,
                                adjacentPoints = graph[adjacentPoint]!!.iterator()))
            } else {
                val pixelNotInPath  = stackOfFrames.pop().point
                pixelsInPath.remove(pixelNotInPath)
            }
        }
        return Result(stackOfFrames.map { it.point }.reversed(), graph)
    }

    private fun containsOpenPixel(grid: List<List<Boolean>>, point: Point<Boolean>): Boolean {
        return (point.row < grid.size && point.column < grid[0].size && grid[point.row][point.column])
    }

}
