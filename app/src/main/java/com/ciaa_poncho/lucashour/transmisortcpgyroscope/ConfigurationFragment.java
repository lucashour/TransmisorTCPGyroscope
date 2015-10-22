package com.ciaa_poncho.lucashour.transmisortcpgyroscope;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class ConfigurationFragment extends Fragment implements View.OnClickListener{

    private EditText ip_address;
    private EditText port_number;
    private Button button;

    public ConfigurationFragment(){}

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View v =  inflater.inflate(R.layout.fragment_config, container, false);

        if(v != null){

            button = ((Button) v.findViewById(R.id.button));
            ip_address = ((EditText) v.findViewById(R.id.ip_address));
            ip_address.setText(GlobalData.getInstance().getIpAddress());
            port_number = ((EditText) v.findViewById(R.id.port_number));
            port_number.setText(String.valueOf(GlobalData.getInstance().getPortNumber()));
        }

        return v;
    }

    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        button.setOnClickListener(this);
    }

    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setHasOptionsMenu(false); //Indicamos que este Fragment no tiene su propio menu de opciones
    }

    public void onClick(View view) {
        if (view.getId() == R.id.button){

            if (ip_address.getText().toString().isEmpty())
                ip_address.setText("192.168.4.1");

            GlobalData.getInstance().setIpAddress(ip_address.getText().toString());

            GlobalData.getInstance().setPortNumber(Integer.parseInt(port_number.getText().toString()));

            Toast.makeText(view.getContext(), "Datos configurados correctamente", Toast.LENGTH_SHORT).show();
        }
    }
}