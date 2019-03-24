package Server;

public interface IntegerBlockingQueue
{
	void addNumber(int i);

	int[] retrieveNumbers();

	int size();
}
