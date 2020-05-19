package stratego;

public class Queue {
	private final Move[] queue;
	private int length;
	
	public Queue(int capacity) {
		queue = new Move[capacity];
		length = 0;
	}
	
	public Move insert(Move move) {
		if(!isFull()) {
			queue[length++] = move;
			return null;
		}
		else {
			Move removed = queue[0];
			for(int i = 0; i < length - 1; i++) {
				queue[i] = queue[i + 1];
			}
			queue[length - 1] = move;
			return removed;
		}	
	}
	
	public Move get(int index) {
		if(index < length)
			return queue[index];
		else
			return null;
	}
	
	public boolean isFull() {
		return length == queue.length;
	}
	
	public Move[] getQueue() {
		return queue;
	}

}
