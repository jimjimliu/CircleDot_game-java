 //Course:         ITI 1121 A
 //Author:         Junhan Liu                                   
 //Assignment:     #3                                      
 //Student number: 7228243                                


public class LinkedQueue<E> implements Queue<E> {
  
  private static class EmptyQueueException extends RuntimeException{
    private EmptyQueueException(String message){
      super(message);
    }
  }

    private static class Elem<T> {

        private T value;
        private Elem<T> next;

        private Elem( T value, Elem<T> next ) {
            this.value = value;
            this.next = next;
        }
    }

    private Elem<E> front;
    private Elem<E> rear;

    public E peek() {
      
      if(front == null && rear == null){
        
        throw new EmptyQueueException("The queue now is empty");
      }
        return front.value;
    }

    public void enqueue( E o ) {
      
      if(o == null){
        throw new NullPointerException("null reference is not allowed");
      }
        Elem<E> newElem;
        newElem = new Elem<E>( o, null );

        if ( rear == null ) {
            front = rear = newElem;
        } else {
            rear.next = newElem;
            rear = newElem;
        }
    }

    public E dequeue() {
      if(front == null && rear == null){
        
        throw new EmptyQueueException("The queue is empty, you cannot dequeue");
      }
        E result = front.value;
        if ( front != null & front.next == null ) {
            front = rear = null;
        } else {
            front = front.next;
        }
        return result;
    }

    public boolean isEmpty() {
        return front == null;
    }

}
