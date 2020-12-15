package com.nzh.note.data_structures.list.base

/**
 *  学习思路：
 * 1、利用哨兵节点(头节点head、尾节点)简化实现难度
 * 2、重点留意边界条件处理
 * 3、配合画图理解 ，不定期复习。
 *
 * 链表中的节点定义：
 */
class Node<T>(var value: T? = null, var next: Node<T>? = null)