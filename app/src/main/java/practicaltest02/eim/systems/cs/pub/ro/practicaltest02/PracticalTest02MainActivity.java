package practicaltest02.eim.systems.cs.pub.ro.practicaltest02;

import android.support.v4.widget.TextViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


public class PracticalTest02MainActivity extends AppCompatActivity {

    ServerThread serverThread = null;
    ClientThread clientThread = null;

    private EditText oras = null;
    private EditText portClient = null;
    private EditText portServer = null;
    private TextView result = null;
    private Button startClient = null;
    private Button startServer= null;

    ClientButtonListener clientButtonListener = new ClientButtonListener();
    private class ClientButtonListener implements Button.OnClickListener{

        @Override
        public void onClick(View view) {
            String clientPort = portClient.getText().toString();
            if (clientPort == null || clientPort.isEmpty()) {
                Toast.makeText(getApplicationContext(), "[MAIN ACTIVITY] Client connection parameters should be filled!", Toast.LENGTH_SHORT).show();
                return;
            }
            if (serverThread == null || !serverThread.isAlive()) {
                Toast.makeText(getApplicationContext(), "[MAIN ACTIVITY] There is no server to connect to!", Toast.LENGTH_SHORT).show();
                return;
            }
            String city = oras.getText().toString();
            if (city == null || city.isEmpty()) {
                Toast.makeText(getApplicationContext(), "[MAIN ACTIVITY] Parameters from client (city)", Toast.LENGTH_SHORT).show();
                return;
            }

            result.setText(Constants.EMPTY_STRING);

            clientThread = new ClientThread(
                    Integer.parseInt(clientPort), city, result
            );
            clientThread.start();
        }


    }
    ServerButtonListener serverButtonListener = new ServerButtonListener();
    private class ServerButtonListener implements Button.OnClickListener{

        @Override
        public void onClick(View view) {
            String serverPort = portServer.getText().toString();
            if (serverPort == null || serverPort.isEmpty()) {
                Toast.makeText(getApplicationContext(), "[MAIN ACTIVITY] Server port should be filled!", Toast.LENGTH_SHORT).show();
                return;
            }
            serverThread = new ServerThread(Integer.parseInt(serverPort));
            if (serverThread.getServerSocket() == null) {
                Log.e(Constants.TAG, "[MAIN ACTIVITY] Could not create server thread!");
                return;
            }
            serverThread.start();
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_practical_test02_main);

        oras = findViewById(R.id.oras);
        portClient = findViewById(R.id.portClient);
        portServer = findViewById(R.id.portServer);
        result = findViewById(R.id.result);

        startClient = findViewById(R.id.startClient);
        startClient.setOnClickListener(clientButtonListener);
        startServer= findViewById(R.id.startServer);
        startServer.setOnClickListener(serverButtonListener);

    }

}

