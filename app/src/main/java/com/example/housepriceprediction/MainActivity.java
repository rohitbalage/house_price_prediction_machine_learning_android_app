package com.example.housepriceprediction;

import androidx.appcompat.app.AppCompatActivity;

import android.content.res.AssetFileDescriptor;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;

import org.tensorflow.lite.Interpreter;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

public class MainActivity extends AppCompatActivity {

    ScrollView sv;
    Interpreter interpreter;

    float[] mean = {-119.564154f,	35.630318f,	28.664505f,	2622.235776f,	535.281659f,	1416.087055f,	496.758167f,	3.869337f,		0.441454f,	0.319405f,	0.000306f,	0.109874f,	0.128961f};
    float[] std = {2.002618f,	2.138574f,	12.556764f,	2169.548287f,	418.469078f,	1103.842065f,	379.109535f,	1.902228f,	0.496576f,	0.466261f,	0.017487f,	0.312742f,	0.335167f};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        try {
            interpreter = new Interpreter(loadModelFile());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        sv = (ScrollView)findViewById(R.id.sv);
        final EditText logED = findViewById(R.id.editText);
        final EditText latED = findViewById(R.id.editText2);
        final EditText ageED = findViewById(R.id.editText3);
        final EditText roomsED = findViewById(R.id.editText4);
        final EditText bedroomsED = findViewById(R.id.editText5);
        final EditText populationED = findViewById(R.id.editText6);
        final EditText householdED = findViewById(R.id.editText15);
        final EditText incomeED = findViewById(R.id.editText16);
        final Spinner ocean = findViewById(R.id.spinner);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getApplicationContext(),android.R.layout.simple_spinner_dropdown_item,
                new String[]{"<1H OCEAN",
                        "INLAND",
                        "ISLAND",
                        "NEAR BAY",
                        "NEAR OCEAN"
                        });
        ocean.setAdapter(arrayAdapter);
        final TextView result = findViewById(R.id.textView2);

        Button btn = findViewById(R.id.button2);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sv.scrollTo( sv.getBottom(),0);
//                float logVal = Float.parseFloat(logED.getText().toString());
//                float latVal = Float.parseFloat(latED.getText().toString());
//                float ageVal = Float.parseFloat(ageED.getText().toString());
//                float roomsVal = Float.parseFloat(roomsED.getText().toString());
//                float bedroomsVal = Float.parseFloat(bedroomsED.getText().toString());
//                float populationVal = Float.parseFloat(populationED.getText().toString());
//                float householdVal = Float.parseFloat(householdED.getText().toString());
//                float incomeVal = Float.parseFloat(incomeED.getText().toString());
//                float oceanA = 0;
//                float oceanB = 0;
//                float oceanC = 0;
//                float oceanD = 0;
//                float oceanE = 0;
//                switch (ocean.getSelectedItemPosition())
//                {
//                    case 0:
//                        oceanA = 1;
//                        oceanB = 0;
//                        oceanC = 0;
//                        oceanD = 0;
//                        oceanE = 0;
//                        break;
//                    case 1:
//                        oceanA = 0;
//                        oceanB = 1;
//                        oceanC = 0;
//                        oceanD = 0;
//                        oceanE = 0;
//                        break;
//                    case 2:
//                        oceanA = 0;
//                        oceanB = 0;
//                        oceanC = 1;
//                        oceanD = 0;
//                        oceanE = 0;
//                        break;
//                    case 3:
//                        oceanA = 0;
//                        oceanB = 0;
//                        oceanC = 0;
//                        oceanD = 1;
//                        oceanE = 0;
//                        break;
//                    case 4:
//                        oceanA = 0;
//                        oceanB = 0;
//                        oceanC = 0;
//                        oceanD = 0;
//                        oceanE = 1;
//                        break;
//                }
//
//                logVal = (logVal - mean[0])/std[0];
//                latVal = (latVal - mean[1])/std[1];
//                ageVal = (ageVal - mean[2])/std[2];
//                roomsVal = (roomsVal - mean[3])/std[3];
//                bedroomsVal = (bedroomsVal - mean[4])/std[4];
//                populationVal = (populationVal - mean[5])/std[5];
//                householdVal = (householdVal - mean[6])/std[6];
//                incomeVal = (incomeVal - mean[7])/std[7];
//                oceanA = (oceanA - mean[8])/std[8];
//                oceanB = (oceanB - mean[9])/std[9];
//                oceanC = (oceanC - mean[10])/std[10];
//                oceanD = (oceanD - mean[11])/std[11];
//                oceanE = (oceanE - mean[12])/std[12];



            }
        });
    }

    //TODO pass input to model and get output
    public float doInference(float[][] input)
    {
        float[][] output = new float[1][1];

        interpreter.run(input,output);

        return output[0][0];
    }

    //TODO load tflite model
    private MappedByteBuffer loadModelFile() throws IOException {
        AssetFileDescriptor assetFileDescriptor = this.getAssets().openFd("house_prediction.tflite");
        FileInputStream fileInputStream = new FileInputStream(assetFileDescriptor.getFileDescriptor());
        FileChannel fileChannel = fileInputStream.getChannel();
        long startOffset = assetFileDescriptor.getStartOffset();
        long length = assetFileDescriptor.getLength();
        return fileChannel.map(FileChannel.MapMode.READ_ONLY, startOffset, length);
    }
}
