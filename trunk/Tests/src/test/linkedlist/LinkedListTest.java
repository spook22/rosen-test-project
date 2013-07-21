package test.linkedlist;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import org.junit.Test;

public class LinkedListTest {

	@Test
	public void testLinkedListNullArray() {
		LinkedList<Integer> list = new LinkedList<Integer>((Integer[]) null);
		assertNull(list.getRoot());
	}

	@Test
	public void testLinkedListIntArray() {
		LinkedList<Integer> list = new LinkedList<Integer>(1, 2, 3, 4, 5);
		Node<Integer> node = list.getRoot();
		assertNotNull(node);
		Integer value = 1;
		while (node != null) {
			assertEquals(value++, node.getData());
			node = node.getNext();
		}
	}

	@Test
	public void testReverse() {
		LinkedList<Integer> list = new LinkedList<Integer>(1, 2, 3, 4, 5);
		list.reverse();
		Node<Integer> node = list.getRoot();
		assertNotNull(node);
		Integer value = 5;
		while (node != null) {
			assertEquals(value--, node.getData());
			node = node.getNext();
		}

		list = new LinkedList<Integer>();
		list.reverse();
	}

	@Test
	public void testReverseEmptyList() {
		LinkedList<Integer> list = new LinkedList<Integer>();
		assertNull(list.getRoot());
		list.reverse();
		assertNull(list.getRoot());
	}

	@Test
	public void testReverseHugeList() {
		LinkedList<Integer> list = new LinkedList<Integer>();
		int max = 1000000;
		int index = 0;
		while (index < max) {
			list.add(index++);
		}
		list.reverse();
		Node<Integer> node = list.getRoot();
		assertNotNull(node);
		Integer value = max - 1;
		while (node != null) {
			assertEquals(value--, node.getData());
			node = node.getNext();
		}
	}

	@Test
	public void testRecursiveReverse() {
		LinkedList<Integer> list = new LinkedList<Integer>();
		int max = 1000;
		int index = 0;
		while (index < max) {
			list.add(index++);
		}
		list.reverse();
		Node<Integer> node = list.getRoot();
		assertNotNull(node);
		Integer value = max - 1;
		while (node != null) {
			assertEquals(value--, node.getData());
			node = node.getNext();
		}
	}

	@Test
	public void testSort() {
		LinkedList<Integer> list = new LinkedList<Integer>();
		int max = 100;
		int index = 0;
		while (index < max) {
			list.add(index++);
		}
		list.reverse();
		System.out.println(list);
		list.sort();
		System.out.println(list);
		Node<Integer> node = list.getRoot();
		assertNotNull(node);
		Integer value = 0;
		while (node != null) {
			assertEquals(value++, node.getData());
			node = node.getNext();
		}

	}
}
