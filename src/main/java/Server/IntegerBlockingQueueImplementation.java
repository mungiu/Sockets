package Server;

import java.util.ArrayList;

/**
 * Part of the adapter pattern together with the Server.IntegerBlockingQueue
 * Capacity of queue is max 20, adapts the arraylist to work as a blocking queue
 * consumer will always retriever 3 numbers at a time so there must be at least 3 numbers in the queue to be retrieved
 */
public class IntegerBlockingQueueImplementation implements IntegerBlockingQueue
{
	private ArrayList<Integer> list;
	private int capacity;

	public IntegerBlockingQueueImplementation(int capacity)
	{
		this.capacity = capacity;
		list = new ArrayList();
	}


	/**
	 * Will wait until capacity is not maximum and then add the number
	 *
	 * @param i
	 */
	@Override
	public synchronized void addNumber(int i)
	{
		while (list.size() >= capacity)
			try
			{
				wait();
			}
			catch (InterruptedException e)
			{
				e.printStackTrace();
			}


		list.add(i);
		notifyAll();    // notifying all threads that a number was added
	}

	/**
	 * Will wait untill 3 numbers exist the return them
	 *
	 * @return
	 */
	@Override
	public synchronized int[] retrieveNumbers()
	{
		while (list.size() < 3)
			try
			{
				wait();
			}
			catch (InterruptedException e)
			{
				e.printStackTrace();
			}

		int[] tempArr = {list.get(0), list.get(1), list.get(2)};

		for (int i : tempArr)
			list.remove(0);
		notifyAll();    //notifying all thread that numbers have been removed

		return tempArr;
	}

	/**
	 * @return the current size of the queue
	 */
	@Override
	public int size()
	{
		return list.size();
	}
}
