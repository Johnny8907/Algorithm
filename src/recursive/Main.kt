package recursive

import java.util.ArrayList

fun main() {
    //1. Sum函数test
    println(factorial(3))

    //2.
    println(sum(intArrayOf(0, 1, 2, 3, 4, 5)))

    //3.
    val list = ArrayList<Any>()
    for (i in 0..54) {
        list.add(Any())
    }
    println(calculateListSize(list))

    //4.
    println(findMaxNum(intArrayOf(0, 2, 5, 8, 7, 9)))

    //5.
    println(binarySearch(intArrayOf(3, 5, 7, 9, 11, 15, 25, 69, 99), 69, 0, 8))
}

private fun factorial(num: Int): Int {
    return if (num == 1) {
        1
    } else {
        factorial(num - 1) * num
    }
}

private fun sum(nums: IntArray): Int {
    return if (nums.size == 1) {
        nums[0]
    } else {
        val tempNums = IntArray(nums.size - 1)
        if (nums.size - 1 >= 0) System.arraycopy(nums, 1, tempNums, 0, nums.size - 1)
        nums[0] + sum(tempNums)
    }
}

private fun calculateListSize(objectList: MutableList<Any>): Int {
    return if (objectList.isEmpty()) {
        0
    } else {
        objectList.removeAt(0)
        1 + calculateListSize(objectList)
    }
}

private fun findMaxNum(nums: IntArray): Int {
    var tNums = nums
    return if (tNums.size == 1) {
        tNums[0]
    } else {
        tNums = removeTheElement(tNums, if (tNums[0] > tNums[1]) 1 else 0)
        findMaxNum(tNums)
    }
}

private fun binarySearch(nums: IntArray, target: Int, head: Int, tail: Int): Int {
    var tHead = head
    var tTail = tail
    return when {
        tHead == tTail -> tHead
        nums[(tHead + tTail) / 2] == target -> (tHead + tTail) / 2
        else -> {
            if (nums[(tHead + tTail) / 2] > target) {
                tTail = (tHead + tTail) / 2
            } else {
                tHead = (tHead + tTail) / 2
            }
            binarySearch(nums, target, tHead, tTail)
        }
    }
}

private fun removeTheElement(arr: IntArray?, index: Int): IntArray {
    // If the array is empty
    // or the index is not in array range
    // return the original array
    if (arr == null
        || index < 0
        || index >= arr.size
    ) {

        return arr!!
    }

    // Create another array of size one less
    val anotherArray = IntArray(arr.size - 1)

    // Copy the elements except the index
    // from original array to the other array
    var i = 0
    var k = 0
    while (i < arr.size) {

        // if the index is
        // the removal element index
        if (i == index) {
            i++
            continue
        }

        // if the index is not
        // the removal element index
        anotherArray[k++] = arr[i]
        i++
    }

    // return the resultant array
    return anotherArray
}
