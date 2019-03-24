package Client;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Random;

import static java.lang.Thread.sleep;


public class ProducerClient
{
	private static final int PORT = 2910;

	public static void main(String[] args)
	{
		try
		{
			accessServer();
		}
		catch (InterruptedException e)
		{
			e.printStackTrace();
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

	private static void accessServer() throws IOException, InterruptedException, ClassNotFoundException
	{
		Random random;
//		socket = new Socket("10.152.194.45", 2910);
		Socket socket = new Socket("localhost", 2910);
		ObjectOutputStream outToServer = new ObjectOutputStream(socket.getOutputStream());

		while (true)
		{
			// creating request for server
			Request sendMessageRequest = new Request();
			sendMessageRequest.type = Request.TYPE.SEND_MESSAGE;
			sendMessageRequest.message = "hardcoded message FROM PRODUCER CLIENT";

			// sending request to server
			outToServer.writeObject(sendMessageRequest);


			System.out.println("Producer: Message sent to server");
			sleep(1000);
		}


	}
}

