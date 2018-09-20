package fiap.com.br.coopfit;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Method;
import java.util.Set;

public class MeasureActivity extends AppCompatActivity {

    Spinner spDispositivos;
    EditText txtInformacao;

    BluetoothAdapter bluetooth = BluetoothAdapter.getDefaultAdapter();
    BluetoothSocket soquete = null;
    OutputStream saida = null;
    Set<BluetoothDevice> dispositivosPareados;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_measure);

        spDispositivos = (Spinner) findViewById(R.id.spDispositivos);
        txtInformacao = (EditText) findViewById(R.id.txtInformacao);


        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item);
        adapter.add("Selecione um dispositivo");
        if (bluetooth != null) {
            if (!bluetooth.isEnabled()) {
                Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                int REQUEST_ENABLE_BT = 1;
                startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
            }//if
            dispositivosPareados = bluetooth.getBondedDevices();
            if ( dispositivosPareados.size() > 0 ) {
                for (BluetoothDevice item : dispositivosPareados) {
                    adapter.add(item.getName());
                }
            }
        }//if
        spDispositivos.setAdapter(adapter);



    }

    private BluetoothSocket criarSoqueteBluetooth(BluetoothDevice dispositivo) throws IOException {
        Method metodo;
        BluetoothSocket tmpSoquete = null;
        try {
            metodo = dispositivo.getClass().getMethod("createRfcommSocket", new Class[] {int.class});
            tmpSoquete = (BluetoothSocket) metodo.invoke(dispositivo, 1);
        } finally {
            return tmpSoquete;
        }
    }

    public void enviar(View view) {

        for (BluetoothDevice item : dispositivosPareados) {
            if (spDispositivos.getSelectedItem().toString().equalsIgnoreCase(item.getName())) {
                try {
                    BluetoothDevice dispositivoRemoto = bluetooth.getRemoteDevice(item.getAddress());
                    soquete = criarSoqueteBluetooth(dispositivoRemoto);
                    soquete.connect();
                    bluetooth.cancelDiscovery();
                    saida = soquete.getOutputStream();
                    byte[] buffer = txtInformacao.getText().toString().getBytes();
                    saida.write(buffer);
                    saida.close();
                    soquete.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
