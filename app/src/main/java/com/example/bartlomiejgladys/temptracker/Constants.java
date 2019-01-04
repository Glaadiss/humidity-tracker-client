package com.example.bartlomiejgladys.temptracker;

import com.google.firebase.iid.FirebaseInstanceId;

public class Constants {

    public static final String MQTT_BROKER_URL = "tcp://35.233.127.48:1883";

    public static final String PUBLISH_TOPIC = "mobile" + FirebaseInstanceId.getInstance().getToken();

    public static final String CLIENT_ID = PUBLISH_TOPIC;
}