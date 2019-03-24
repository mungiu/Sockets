package Client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ConsumerClient {
	private static final int PORT = 2910;

	public static void main(String[] args) {
		try {
			accessServer();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	private static void accessServer() throws IOException, ClassNotFoundException {
		Socket socket = new Socket("localhost", PORT);

		ObjectOutputStream outToServer = new ObjectOutputStream(socket.getOutputStream());
		ObjectInputStream inFromServer = new ObjectInputStream(socket.getInputStream());

		while (true) {
			Request requestMessageRequest = new Request();
			requestMessageRequest.type = Request.TYPE.REQUEST_MESSAGE;
			outToServer.writeObject(requestMessageRequest);

			Request receivedRequestFromServer = ((Request) inFromServer.readObject());

			switch (receivedRequestFromServer.type) {
				case SEND_MESSAGE:
					String tempMessage = receivedRequestFromServer.message;
					System.out.println("Consumer received: '" + tempMessage + "'");

					// creating request for server
					Request confirmationRequest = new Request();
					confirmationRequest.type = Request.TYPE.CONFIRMATION_MESSAGE;
					confirmationRequest.message= "Consumer confirms receipt of message";

					// sending request to the server
					outToServer.writeObject(confirmationRequest);
					break;
			}
		}
	}
}

