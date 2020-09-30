package in.dotworld.workmanager;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.work.BackoffPolicy;
import androidx.work.Constraints;
import androidx.work.Data;
import androidx.work.NetworkType;
import androidx.work.OneTimeWorkRequest;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkInfo;
import androidx.work.WorkManager;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {


    TextView oneTime,periodic,chaining;
    WorkManager mWorkmanager;
    Button onetime,periodic_request,chaining_request;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        oneTime=findViewById(R.id.oneTime);
        periodic=findViewById(R.id.periodic);
        chaining=findViewById(R.id.chaining);


        onetime=findViewById(R.id.onetime);

        periodic_request=findViewById(R.id.periodic_request);

        chaining_request=findViewById(R.id.chaining_request);

    }

    @Override
    public void onStart(){
        super.onStart();

        onetime.setOnClickListener(this);

        periodic_request.setOnClickListener(this);

        chaining_request.setOnClickListener(this);

    }


    public void excuteOneTimeRequest(){
        Constraints constraints=new Constraints.Builder()
                .setRequiresBatteryNotLow(true)
                .setRequiresCharging(false)
                .setRequiredNetworkType(NetworkType.CONNECTED)
                .setRequiresDeviceIdle(false)
                .build();

        final OneTimeWorkRequest oneTimeWorkRequest=new OneTimeWorkRequest.Builder(Myworker.class).setConstraints(constraints).build();

        mWorkmanager.getInstance(getApplicationContext()).enqueue(oneTimeWorkRequest).getResult();

        WorkManager.getInstance(getApplicationContext()).getWorkInfoByIdLiveData(oneTimeWorkRequest.getId())
                .observe(this, new Observer<WorkInfo>() {
                    @Override
                    public void onChanged(WorkInfo workInfo) {
                        String status=workInfo.getState().toString();
                        oneTime.append("\n"+status);
                        Data data=workInfo.getOutputData();
                        Log.i("MainActivity",status);
                        if (workInfo.getState().isFinished()) {
                            oneTime.append("\n"+data.getString("value"));
                        }
                        Toast.makeText(getApplicationContext(),status,Toast.LENGTH_SHORT).show();
                    }
                });

        findViewById(R.id.onetime_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WorkManager.getInstance(getApplicationContext()).cancelWorkById(oneTimeWorkRequest.getId());
            }
        });
    }


    public void excutePeriodicRequest() {

        Constraints constraints=new Constraints.Builder()
                .setRequiresBatteryNotLow(true)
                .setRequiresCharging(false)
                .setRequiredNetworkType(NetworkType.CONNECTED)
                .setRequiresDeviceIdle(false)
                .build();

        final PeriodicWorkRequest periodicWorkRequest=new PeriodicWorkRequest.Builder(NewWorker.class,15,TimeUnit.MINUTES,5,TimeUnit.MINUTES)
                .setConstraints(constraints).build();

        mWorkmanager.getInstance(getApplicationContext()).enqueue(periodicWorkRequest).getResult();

        WorkManager.getInstance(getApplicationContext()).getWorkInfoByIdLiveData(periodicWorkRequest.getId())
                .observe(this, new Observer<WorkInfo>() {
                    @Override
                    public void onChanged(WorkInfo workInfo) {
                        String status=workInfo.getState().toString();
                        periodic.append("\n"+status);
                    }
                });
        findViewById(R.id.periodic_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WorkManager.getInstance(getApplicationContext()).cancelWorkById(periodicWorkRequest.getId());
            }
        });

    }

    public void excuteChainRequest(){

        Constraints constraints=new Constraints.Builder().setRequiresBatteryNotLow(true)
                .setRequiredNetworkType(NetworkType.CONNECTED)
                .setRequiresCharging(false)
                .setRequiresDeviceIdle(false)
                .build();

        final OneTimeWorkRequest oneTimeWorkRequest1 =new OneTimeWorkRequest.Builder(Myworker.class).build();
        OneTimeWorkRequest oneTimeWorkRequest2 =new OneTimeWorkRequest.Builder(NewWorker.class).build();
        OneTimeWorkRequest oneTimeWorkRequest3 =new OneTimeWorkRequest.Builder(Myworker.class).build();

      mWorkmanager.getInstance(getApplicationContext()).beginWith(oneTimeWorkRequest1)
                .then(oneTimeWorkRequest2)
                .then(oneTimeWorkRequest3)
                .enqueue().getResult();

        WorkManager.getInstance(getApplicationContext()).getWorkInfoByIdLiveData(oneTimeWorkRequest1.getId())
                .observe(this, new Observer<WorkInfo>() {
                    @Override
                    public void onChanged(WorkInfo workInfo) {
                        String staus=workInfo.getState().toString();
                        chaining.append("\n"+staus);
                    }
                });

        findViewById(R.id.chaining_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WorkManager.getInstance(getApplicationContext()).cancelAllWork();
            }
        });

    }
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.onetime:
                excuteOneTimeRequest();       //OneTimeRequest
                break;

            case R.id.periodic_request:
                excutePeriodicRequest();     //PeriodicRequest
                break;

            case R.id.chaining_request:
                excuteChainRequest();        //ChainingRequest
                break;
        }
    }
}