package Server;


import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * This class handles interactions with the queue by using the Adapter class for the queue.
 */
public class Server
{
	private static final int PORT = 2910;
	private static ServerSocket serverSocket;
	private IntegerBlockingQueue intBlockQueue;

	public Server(IntegerBlockingQueue intBlockQueue) throws IOException
	{
		this.intBlockQueue = intBlockQueue;
		serverSocket = new ServerSocket(PORT);
		System.out.println("Server: started...");
	}

	public static void main(String[] args)
	{
		IntegerBlockingQueue intBlockQueue = new IntegerBlockingQueueImplementation(6);

		try
		{
			new Server(intBlockQueue).startServer();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}


	public void startServer() throws IOException
	{
		while (true)
		{
			System.out.println("Server: Waiting for client connection");
			// listen to connections to be made to this socket, blobks untill connection established
			Socket socket = serverSocket.accept();
			System.out.println("Server: New connection established");
			new Thread(new ServerSocketHandler(socket, intBlockQueue)).start();
		}
	}
}
