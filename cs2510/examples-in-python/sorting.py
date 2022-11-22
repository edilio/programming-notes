# Sorting
import heapq


def merge_sort(lst):
    """
    :param lst: unsorted list
    :return: a sorted list
    function to sort a list using merge sort, a divide and conquer algorithm.
    """
    lst = lst or []
    if len(lst) < 2:
        return lst[:]
    else:
        middle = len(lst) // 2
        print('middle', middle)
        left = merge_sort(lst[:middle])
        print('left', left)
        right = merge_sort(lst[middle:])
        print('right', right)
        return merge(left, right)


def merge(left, right):
    result = []
    i, j = 0, 0
    while i < len(left) and j < len(right):
        if left[i] <= right[j]:
            result.append(left[i])
            i += 1
        else:
            result.append(right[j])
            j += 1
    while i < len(left):
        result.append(left[i])
        i += 1
    while j < len(right):
        result.append(right[j])
        j += 1
    return result


def quicksort(lst):
    """
    :param lst: unsorted list
    :return: a sorted list
    function to sort a list using quick sort, a divide and conquer algorithm.
    """
    if len(lst) < 2:
        return lst[:]
    else:
        pivot = lst[0]
        less = [i for i in lst[1:] if i <= pivot]
        greater = [i for i in lst[1:] if i > pivot]
        return quicksort(less) + [pivot] + quicksort(greater)


def heapsort(lst):
    """
    :param lst: unsorted list
    :return: a sorted list
    function to sort a list using heap sort, a divide and conquer algorithm.
    """
    lst = lst or []
    heap = []
    for value in lst:
        heapq.heappush(heap, value)
    return [heapq.heappop(heap) for i in range(len(heap))]


def main():
    lst = [5, 3, 6, 2, 10, 1, 4, 9, 8, 7]
    print('Unsorted list:', lst)
    print(f'merge: {merge_sort(lst)}')
    print(f'quick: {quicksort(lst)}')
    print(f'heap:  {heapsort(lst)}')


if __name__ == '__main__':
    main()
