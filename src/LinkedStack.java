 //Course:         ITI 1121 A
 //Author:         Junhan Liu                                   
 //Assignment:     #3                                      
 //Student number: 7228243                                


public class LinkedStack<E> implements Stack<E>{
  
  private static class EmptyStackException extends RuntimeException{
    private EmptyStackException(String message){
      super(message);
    }
  }

    private static class Elem<T>{
        private T info;
        private Elem<T> next;
        private Elem( T info, Elem<T> next) {
            this.info = info;
            this.next = next;
        }
    }

    private Elem<E> top; // instance variable
    private int size;

    public LinkedStack() {
        top = null;
    }

    public boolean isEmpty() {
        return top == null;
    }

    public void push( E info ) {
      
      if(info == null){
        throw new NullPointerException("null reference cannot be added into stack");
      }
        top = new Elem<E>( info, top );
        size++;
    }

    public E peek() {
      
      if(top == null){
        throw new EmptyStackException("The Stack is empty");
      }
        return top.info;
    }

    public E pop() {
      
      if(top == null){
        throw new EmptyStackException("The stack is empty, you cannot pop anything");
      }

        
        E savedInfo = top.info;

        Elem<E> oldTop = top;
        Elem<E> newTop = top.next;

        top = newTop;

        oldTop.info = null; // scrubbing the memory
        oldTop.next = null; // scrubbing the memory
        
        size--;

        return savedInfo;
    }
    
    public int size(){
      
      return size;
    }
      
}
