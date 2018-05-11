package com.example.powder.monica;

import android.content.Intent;
import android.os.Environment;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Calendar;
import java.util.jar.Attributes;

public class NewMeetingActivity extends AppCompatActivity {

    private Button confirmButton;
    private TextInputEditText meetingName;
    private File file;
    private File directory;
    private PrintWriter writer;
    private TextInputEditText inputEmail;
    private String recorderName;
    private String mailSubject;
    private String path;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new);
        confirmButton = findViewById(R.id.confirmThis);
        meetingName = findViewById(R.id.meetingNameInput);
        inputEmail = findViewById(R.id.emailInput);
        recorderName = getIntent().getExtras().getString("recorderName");

        confirmButton.setOnClickListener((view) -> {
                    path = Environment.getExternalStorageDirectory().getPath() + "/" + recorderName;
                    directory = new File(path);
                    File[] files = directory.listFiles();

                    for (File file : files){
                        if(meetingName.getText().toString().compareTo(file.getName().toString()) == 0){
                            Toast.makeText(getApplicationContext(), "Spotkanie o takiej nazwie już istnieje", Toast.LENGTH_SHORT).show();
                            return;
                        }
                    }

                        if (meetingName.getText().length() == 0) {
                            Toast.makeText(getApplicationContext(), "Nazwa spotkania nie może być pusta!", Toast.LENGTH_SHORT).show();
                            return;
                        }
                    mailSubject = meetingName.getText() + " "
                            + Calendar.getInstance().get(Calendar.DAY_OF_MONTH) + "/"
                            + (Calendar.getInstance().get(Calendar.MONTH) + 1) + "/"
                            + Calendar.getInstance().get(Calendar.YEAR) + " godz. "
                            + Calendar.getInstance().get(Calendar.HOUR_OF_DAY) + "꞉"
                            + Calendar.getInstance().get(Calendar.MINUTE);

                    path += "/" + meetingName.getText().toString();
                    directory = new File(path);
                    if (!directory.exists()) {
                        directory.mkdirs();
                    }

                    file = new File(path, "email.txt");

                    try {
                        writer = new PrintWriter(path + "/email.txt");
                    } catch (FileNotFoundException e) {
                        System.out.println(e.getMessage());
                    }
                    if (writer != null) {
                        writer.println(mailSubject);
                        writer.println(inputEmail.getText().toString());
                        writer.close();
                    }
                    Intent newIntent = new Intent(NewMeetingActivity.this, AudioOnTouchActivity.class);
                    newIntent.putExtra("Name", meetingName.getText().toString());
                    newIntent.putExtra("recorderName", recorderName.toString());
                    newIntent.putExtra("mailSubject", mailSubject.toString());
                    startActivity(newIntent);
                }

        );

    }
}
