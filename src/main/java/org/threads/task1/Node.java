package org.threads.task1;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Node<K,V> {

    int hashcode;
    K key;
    V value;
    Node next;

    public Node(final K key, final V value, final Node next, final int hashcode) {
        this.key = key;
        this.value = value;
        this.hashcode = hashcode;
        this.next = next;
    }

}
