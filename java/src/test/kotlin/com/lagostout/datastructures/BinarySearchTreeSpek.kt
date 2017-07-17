package com.lagostout.datastructures

import com.lagostout.common.BinaryTreeNode
import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.*
import java.util.*
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertNull
import kotlin.test.assertTrue

class BinarySearchTreeSpek : Spek({
    describe("BinarySearchTree") {

        val tree = BinarySearchTree<Int>()
        val key = 1

        describe("inserting a key") {
            context("tree is empty") {
                beforeEachTest {
                    tree.insert(key)
                }
                it("should have a non-null root") {
                    assertNotNull(tree.root)
                }
                it("should contain the inserted key") {
                    assertTrue(contains(tree.root, key))
                }
                it("should contain only one key") {
                    assertTrue(size(tree.root) == 1)
                }
            }
            xcontext("tree is not empty") {
                beforeEachTest {
                    tree.insert(key)
                }
                it("should contain the inserted and existing keys") {
                    val key2 = 2
                    tree.insert(key2)
                    assertTrue(tree.contains(key2))
                }
                it("maintains the binary search tree property") {
                    assertTrue(maintainsBinarySearchTreeProperty(tree.root!!))
                }
            }
        }

        xdescribe("finding a key") {
            context("tree is empty") {
                it("should return null") {
                    val keyToFind = 3
                    assertNull(tree.find(keyToFind))
                }
            }
            context("tree isn't empty") {
                beforeEachTest {
                    val keys = listOf(1,5,6,2,4)
                    keys.forEach { tree.insert(it) }
                }
                context("key is in tree") {
                    val keyToFind = 2
                    it("should return the node containing the key") {
                        assertEquals(keyToFind, tree.find(keyToFind)?.value)
                    }
                }
                context("key isn't in tree") {
                    it("should return null") {
                        val keyToFind = 11
                        assertNull(tree.find(keyToFind))
                    }
                }
            }
        }

    }
}) {
    companion object {
        fun <T : Comparable<T>> maintainsBinarySearchTreeProperty(
                root: BinaryTreeNode<T>): Boolean {

            return false
        }
        fun <T : Comparable<T>> contains(
                root: BinaryTreeNode<T>?, key: T): Boolean {

            return false
        }
        fun <T : Comparable<T>> size(root: BinaryTreeNode<T>?): Int {
            var count = 0
            if (root == null) return count
            val queue = LinkedList<List<BinaryTreeNode<T>>>()
            queue.push(listOf(root))
            while (queue.isNotEmpty()) {
                val level = queue.poll()
                count += level.size
                val nextLevel = level.fold(mutableListOf<BinaryTreeNode<T>>()) {
                    acc, node ->
                    acc.addAll(listOf(node.left, node.right).filterNotNull())
                    acc
                }
                queue.add(nextLevel)
            }
            return count
        }
    }
}