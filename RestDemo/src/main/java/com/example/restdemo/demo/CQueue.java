package com.example.restdemo.demo;

import java.util.Stack;

public class CQueue {
    Stack stackA;
    Stack stackB;
    public CQueue() {
        stackA = new Stack();//作为第一个
        stackB = new Stack();
    }

    public void appendTail(int value) {
        stackA.push(value);
    }

    public int deleteHead() {
//        删除的思想
//          1.将A栈的所有元素出栈，入栈B
//        2.栈顶元素出站
//        3.栈B所有元素出栈，入栈A
        if (stackA.empty()){
            return -1;
        }
        for (int i = 0; i < stackA.size(); i++) {
           stackB.push(stackB.pop());
        }
        Object pop = stackB.pop();
        stackA=stackB;
        stackB=null;
        return (int)pop;
    }
}
