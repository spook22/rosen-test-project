package rspasov.sort;

import java.util.Arrays;

public class SortingAlgorithmsTester {
	
	private static boolean printArr = false;
	
	public static void main(String[] args) {
		for (int i = 0; i < 100; i++) {
			int[] arr = generateArray(1000);
			heapSort(arr);
			checkOrder(arr);
		}
	}
	
	private static void print(int[] arr) {
		if (printArr) {
			System.out.println(Arrays.toString(arr));
		}
	}
	
	public static void heapSort(int[] arr) {
		buildHeap(arr, arr.length);
		int index = arr.length - 1;
		while (index > 0 ) {
			swap(arr, 0, index);
			buildHeap(arr, index--);
		}
	}

	private static void buildHeap(int[] arr, int length) {
		int index = 0;
		while (index < length) {
			int swapIndex = index;
			// For each element, compare the parent with its left and right child and swap it with the smallest element of them three.
			int childIndex = index * 2 + 1; // Compare with left child
			if (childIndex < length && arr[swapIndex] < arr[childIndex]) { 
				swapIndex = childIndex;
			}
			childIndex = index * 2 + 2; // Compare with right child
			if (childIndex < length && arr[swapIndex] < arr[childIndex]) {
				swap(arr, index, childIndex);
				siftUp(arr, index);
			}
			if (swapIndex != index) {
				swap(arr, index, swapIndex);
				siftUp(arr, index);
			} else {
				index++;
			}
		}
		checkHeap(arr, length);
	}
	
	private static void checkHeap(int[] arr, int length) {
		for (int index = 0; index < length; index++) {
			int childIndex = index * 2 + 1;
			if (childIndex < length && arr[index] < arr[childIndex]) { // Compare with left child
				throw new RuntimeException("Heap is out of order at index " + index + ". Heap: " + Arrays.toString(arr));
			}
			childIndex = index * 2 + 2;
			if (childIndex < length && arr[index] < arr[childIndex]) { // Compare with right child
				throw new RuntimeException("Heap is out of order at index " + index + ". Heap: " + Arrays.toString(arr));
			}
		}
	}
	
	private static void siftUp(int[] arr, int index) {
		int parentIndex = (index - 1) / 2;
		if (parentIndex >= 0 && arr[parentIndex] < arr[index]) {
			swap(arr, parentIndex, index);
			siftUp(arr, parentIndex);
		}
	}

	private static int[] generateArray(int size) {
		int[] result = new int[size];
		for (int index = 0; index < size; index++) {
			result[index] = (int) Math.round(Math.random() * size);
		}
		print(result);
		return result;
	}
	
	private static void checkOrder(int[] arr) {
		for (int index = 0; index < arr.length - 1; index++) {
			if (arr[index] > arr[index + 1]) {
				throw new RuntimeException("Array is not sorted at index " + index + ". Array: " + Arrays.toString(arr));
			}
		}
	}

	private static void swap(int[] arr, int i, int j) {
		int temp = arr[i];
		arr[i] = arr[j];
		arr[j] = temp;
		print(arr);
	}
	

}
