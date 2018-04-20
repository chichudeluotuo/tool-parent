/**
 * Project Name:tool-common
 * File Name:StackSeries.java
 * Package Name:com.luotuo.tool.common.utils.arithmetic
 * Date:2018年4月20日上午9:24:14
 * Copyright (c) 2018, 彳亍的骆驼 All Rights Reserved.
 *
*/

package com.luotuo.tool.common.utils.arithmetic;

import java.util.Stack;

/**
 * ClassName: Stack <br/>
 * Function:1.实现一个栈，带有出栈(pop),入栈(push),取最小元素(getMin)三个方法，保证三个方法的时间复杂度为O(1). <br/>
 * Reason: TODO ADD REASON(可选). <br/>
 * date: 2018年4月20日 上午9:45:08 <br/>
 *
 * @author 鲁济良
 * @since JDK 1.8
 */
public class LinkedStack<E>{
	
	private Node<E> node;
	
	public LinkedStack() {
		this.node = new Node<E>();
	}

	public E getMin() {

	
		return null;
	}
	
	/**
	 * push:(将元素压入栈底). <br/>
	 * TODO(描述这个方法的注意事项 – 可选).<br/>
	 *
	 * @author pactera
	 * @param value 压栈数据
	 * @return E
	 * @since JDK 1.8
	 */
	public E push(E value) {
		
		Node<E> node2 = new Node<E>(value);
		node.next = node2;
		node = node2;
		
		return value;
	}
	
	public E pop () {
		
		return node.value;
	}

}

class Node<E>{
	
	public int index;
	public Node<E> next;
	public E value;
	
	public Node() {
		
		this.index = 0;//索引起点0
		next = null;//初始下个元素为null
		
	}
	
	public Node(E value) {
		
		this.index++;//索引起点0
		this.next = null;//初始下个元素为null
		this.value = value;
	}
	
}
