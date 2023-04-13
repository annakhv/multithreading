package org.threads.task1;

import java.util.NoSuchElementException;

import lombok.ToString;

@ToString
public class ThreadSafeMap<K, V> {

    private Node[] hashTable = new Node[10];

    public void put(K key, V value) {
        Node<K, V> node = new Node<>(key, value, null, key.hashCode());
        int index = calculateBacketNumber(key);
        Node<K,V> existingNode = hashTable[index];
        if (existingNode == null) {
            hashTable[index] = node;
        } else {
            Node<K,V> curNode = existingNode;
            Node<K,V> prevNode=existingNode;
            while (curNode.getNext() != null) {
                if (key.hashCode() == curNode.getKey()
                        .hashCode() && key.equals(curNode.getKey())) {
                    if(prevNode!=curNode) {
                        System.out.println(prevNode);
                        System.out.println(curNode);
                        System.out.println("if condition");
                        node.setNext(curNode.getNext());
                        curNode = node;   //replace in the middle
                        prevNode.setNext(curNode);
                    }else{
                        System.out.println(prevNode);
                        System.out.println(curNode);
                        System.out.println("else condition");
                        hashTable[index]=node;
                    }
                    return;
                } else {
                    prevNode=curNode;
                    curNode = curNode.getNext();

                }
            }
            if (key.hashCode() == curNode.getKey()  //check the last non null node
                    .hashCode() && key.equals(curNode.getKey())) {
                System.out.println("print");
                if(prevNode != curNode) {  // when it is not first element in linkdelist
                    System.out.println(prevNode);
                    System.out.println(curNode);
                    System.out.println("if condition");
                    curNode = node; //replace edge node
                    prevNode.setNext(curNode);
                }else{
                    System.out.println(prevNode);
                    System.out.println(curNode);
                    System.out.println("else condition");
                    hashTable[index]=node;
                }

            } else {

                curNode.setNext(node);
            }

        }
    }

    public V getValue(K key) {
        int hashcode = key.hashCode();
        int backetNumber = hashcode % hashTable.length;
        Node<K,V> startNode = hashTable[backetNumber];
        if (startNode == null) {
            throw new NoSuchElementException("no element for that key " + key);
        } else {
            Node<K,V> curNode = startNode;
            while (curNode.getNext() != null) {
                if (key.hashCode() == curNode.getKey()
                        .hashCode() && key.equals(curNode.getKey())) {
                    return curNode.getValue();
                } else {
                    curNode = curNode.getNext();
                }
            }
            if (key.hashCode() == curNode.getKey()
                    .hashCode() && key.equals(curNode.getKey())) {
                return curNode.getValue();  //check the last non null node
            } else {
                throw new NoSuchElementException("no element for that key " + key);
            }
        }
    }

    private int calculateBacketNumber(K key) {
        int hashcode = key.hashCode();
        int index = hashcode % hashTable.length;
        return index;
    }

    public static void main(String[] args) {
        ThreadSafeMap<Integer, Integer> map = new ThreadSafeMap<>();
        map.put(1, 3);
        map.put(1,4);
        map.put(9, 3);
        map.put(9, 30);
        map.put(90, 1);
        map.put(70,80);
        map.put(10,10);
        map.put(11, 3);
        map.put(22, 3);
        map.put(23, 30);
        map.put(33, 45);
        map.put(23, 1);
        map.put(43, 888);
        map.put(53, 5);
        map.put(43, 999);
        System.out.println(map);
        System.out.println("getting elements");
        System.out.println("get value for key 70 "+ map.getValue(70));
        System.out.println("get value for key 70 "+ map.getValue(10));
        System.out.println("get value for key 33 "+ map.getValue(33));
        System.out.println("get value for key 23 "+ map.getValue(23));
        System.out.println("get value for key 53 "+ map.getValue(53));
        System.out.println("get value for key 25"+ map.getValue(25));
    }
}
