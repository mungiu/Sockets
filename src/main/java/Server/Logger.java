package Server;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Singleton class printing out to the console, thread safe
 */
public class Logger
{
	// making the variable static ensures its instance is attached to this class only not to instances of it
	private static Logger instance;
	private static Lock lock = new ReentrantLock();

	// private so nobody from out can create it (singletone)
	private Logger()
	{

	}

	/**
	 * Lazy instantiation of the instance, also thread safe dues to double
	 * checking before and after the  entrance into the synchronize code part
	 *
	 * @return
	 */
	public static Logger getInstance()
	{
		if (instance == null)
			synchronized (lock)
			{
				if (instance == null)
					instance = new Logger();
			}

		return instance;
	}

	public synchronized void log(String messageToBeLogged)
	{
		System.out.println("Log : " + messageToBeLogged);
	}
}
