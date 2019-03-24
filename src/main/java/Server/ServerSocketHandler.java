package Server;


import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ServerSocketHandler implements Runnable
{
	private IntegerBlockingQueue intBlockQueue;
	private Socket socket;

	public ServerSocketHandler(Socket socket, IntegerBlockingQueue intBlockQueue)
	{
		this.socket = socket;
		this.intBlockQueue = intBlockQueue;

	}

	public void handleAddNumRequest(Request receivedRequest) throws IOException, ClassNotFoundException
	{
		intBlockQueue.addNumber(receivedRequest.number);
		Logger.getInstance().log("ServerSocketHandler: Received number");
	}


	public void handleGiveNumArr(ObjectOutputStream outToClient) throws IOException
	{
		// creating the request for consumer client
		Request calcNumsRequest = new Request();
		calcNumsRequest.type = Request.TYPE.CALCULATE_NUM;
		calcNumsRequest.numArr = intBlockQueue.retrieveNumbers();

		// sending the request
		outToClient.writeObject(calcNumsRequest);
	}

	@Override
	public void run()
	{
		try
		{
			ObjectOutputStream outToClient = new ObjectOutputStream(socket.getOutputStream());
			ObjectInputStream inFromClient = new ObjectInputStream(socket.getInputStream());

			while (true)
			{
				Logger.getInstance().log("ServerSocketHandler: Preparing to listen for client request...");
				Request receivedRequest = (Request) inFromClient.readObject();

				switch (receivedRequest.type)
				{
					case ADD_NUM:
						handleAddNumRequest(receivedRequest);
						break;
					case PRINT_NUM:
						Logger.getInstance().log("ServerSocketHandler received calculated result: " + receivedRequest.number);
						break;
					case GIVE_NUMARR:
						Logger.getInstance().log("ServerSocketHandler: Starting output stream...");
						handleGiveNumArr(outToClient);
						break;
				}
			}
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		catch (ClassNotFoundException e)
		{
			e.printStackTrace();
		}
	}
}