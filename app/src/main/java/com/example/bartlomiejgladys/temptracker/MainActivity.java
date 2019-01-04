package com.example.bartlomiejgladys.temptracker;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.bartlomiejgladys.temptracker.model.Response;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.eclipse.paho.android.service.MqttAndroidClient;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallbackExtended;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import java.io.UnsupportedEncodingException;

public class MainActivity extends AppCompatActivity {
    private MqttAndroidClient client;
    private MqttAndroidClient publisher;
    private String TAG = "MainActivity";
    private ObjectMapper mapper = new ObjectMapper();
    private Response response;
    TextView tempInside;
    TextView tempOutside;
    TextView humiInside;
    TextView humiOutside;
    EditText tempMin;
    EditText tempMax;
    EditText humiMin;
    EditText humiMax;
    Button save;

    private MqttAndroidClient getMqttClient(String clientId) {
        return new PahoMqttClient().getMqttClient(getApplicationContext(), Constants.MQTT_BROKER_URL, clientId);
    }

    public void updateInputs(String s, MqttMessage mqttMessage) {
        try {
            response = mapper.readValue(new String(mqttMessage.getPayload()), Response.class);
            tempInside.setText(String.format("%s *C", String.valueOf(response.getTemperature())));
            tempOutside.setText(String.format("%s *C", String.valueOf(response.getTemperatureOut())));
            humiInside.setText(String.format("%s %%", String.valueOf(response.getHumidity())));
            humiOutside.setText(String.format("%s %%", String.valueOf(response.getHumidityOut())));
            tempMin.setText(String.valueOf(response.getTemperatureMin()));
            tempMax.setText(String.valueOf(response.getTemperatureMax()));
            humiMin.setText(String.valueOf(response.getHumidityMin()));
            humiMax.setText(String.valueOf(response.getHumidityMax()));
        } catch (Exception e) {
            Log.i(TAG, e.getMessage());
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        client = getMqttClient(Constants.CLIENT_ID);
        publisher = getMqttClient(Constants.CLIENT_ID + "publisher");
        subscribeClient();
        initializeInputs();
        setSaveListener();
    }

    private void initializeInputs() {
        tempInside = findViewById(R.id.tempInside);
        tempOutside = findViewById(R.id.tempOutside);
        humiInside = findViewById(R.id.humiInside);
        humiOutside = findViewById(R.id.humiOutside);
        tempMin = findViewById(R.id.tempMin);
        tempMax = findViewById(R.id.tempMax);
        humiMax = findViewById(R.id.humiMax);
        humiMin = findViewById(R.id.humiMin);
        save = findViewById(R.id.save);
    }

    private void setSaveListener() {
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                response.setHumidityMin(Float.parseFloat(String.valueOf(humiMin.getText())));
                response.setHumidityMax(Float.parseFloat(String.valueOf(humiMax.getText())));
                response.setTemperatureMin(Float.parseFloat(String.valueOf(tempMin.getText())));
                response.setTemperatureMax(Float.parseFloat(String.valueOf(tempMax.getText())));
                try {
                    String msg = mapper.writeValueAsString(response);
                    PahoMqttClient.publishMessage(publisher, msg, 1, Constants.PUBLISH_TOPIC);
                } catch (JsonProcessingException | MqttException | UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void subscribeClient() {

        client.setCallback(new MqttCallbackExtended() {
            @Override
            public void connectComplete(boolean b, String s) {
                try {
                    PahoMqttClient.subscribe(client, Constants.CLIENT_ID, 1);
                } catch (MqttException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void connectionLost(Throwable throwable) {

            }

            @Override
            public void messageArrived(String s, MqttMessage mqttMessage) {
                updateInputs(s, mqttMessage);
            }

            @Override
            public void deliveryComplete(IMqttDeliveryToken iMqttDeliveryToken) {

            }
        });
    }

}
