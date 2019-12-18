package com.example.mystuff6;

import android.app.Activity;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final EditText etText = (EditText) findViewById(R.id.editText);
        final TextView txtView = (TextView) findViewById(R.id.textView);
        Button bttn = (Button) findViewById(R.id.button2);
        Button bttn2 = (Button) findViewById(R.id.button3);
        final ProgressBar bProgress = (ProgressBar) findViewById(R.id.progressBar);

        final AsyncTest asyncTest = new AsyncTest(this);

        bttn2.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                
                if(asyncTest.getStatus().equals(AsyncTask.Status.RUNNING)){
                    Toast.makeText(MainActivity.this, "Pracuje", Toast.LENGTH_SHORT).show();
                }else{

                    if(asyncTest.getStatus().equals(AsyncTask.Status.FINISHED)) {
                        AsyncTest asyncTest_1 = new AsyncTest(MainActivity.this);
                        asyncTest_1.execute(10);
                    }else {
                        asyncTest.execute(20);
                    }
                }

            }
        });


        bttn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final int range = Integer.parseInt(etText.getText().toString());
                bProgress.setMax(range);
                Thread th = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            final int max = range;
                            for (int i = 0; i <= max; i++) {
                                Thread.sleep(1000);

                                final int number = i;
                                txtView.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        txtView.setText(number + "/" + max);
                                        bProgress.setProgress(number);
                                    }
                                });
                            }
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                });
                th.start();
            }
        });


    }

     public class AsyncTest extends AsyncTask<Integer, Integer, Void> {

        Activity conn;
        ProgressBar asyncProg;
        final TextView asyncTxtView;

        public AsyncTest(Activity act){
            this.conn = act;
            asyncTxtView = (TextView)conn.findViewById(R.id.textView);
            asyncProg = (ProgressBar)conn.findViewById(R.id.progressBar);

        }


        @Override
        protected Void doInBackground(Integer... integers) {
            final int max = integers[0];

            try {
                for (int i = 0; i <= max; i++) {
                    Thread.sleep(1000);
                    publishProgress(i);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return null;
        }

         @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            asyncProg.setProgress(values[0]);
            asyncProg.setProgress(values[0]);
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            Toast.makeText(conn, "Zakonczono", Toast.LENGTH_LONG);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            asyncProg.setMax(20);
        }
    }
}

