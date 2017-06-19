package com.myhexaville.overlayapp;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

public class MainActivity extends AppCompatActivity {
    private static final String LOG_TAG = "MainActivity";
    public static final int RC_OVERLAY_PERMISSION = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (!Settings.canDrawOverlays(this)) {
            Intent myIntent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION);
            myIntent.setData(Uri.parse("package:" + getPackageName()));
            startActivityForResult(myIntent, RC_OVERLAY_PERMISSION);
        } else {
            startService(new Intent(this, NoiseService.class).putExtra("size", 5000));
        }

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 101) {
            startService(new Intent(this, NoiseService.class));

        }
    }
}
/*
*
* <img class="alignnone size-full wp-image-988" src="http://myhexaville.com/wp-content/uploads/2017/06/abstract-1866956_1280-e1497586807749.jpg" alt="" width="1280" height="617" />

Let's look at Android Bundle class and how it works. It has a pretty easy api, but how do they actually work under the hood?

<!--more-->
<h2>Bundle</h2>
We use Android Bundle class implicitly when putting passing data between activities through Intent with methods like this <em>Intent#putExtra. </em>This method delegates it to related Bundle method: <em>Bundle#putString/putByte</em> etc.

Also we use Bundle when restoring activity state in <em>Activity#onSaveInstanceState/onRestoreInstanceState</em> and you get it in <em>onCreate</em> as parameter as well

And Bundle stores this data in a simple ArrayMap with key values, but that's not where the magic happens
<h2>Parcelable</h2>
You're probably familiar with Parceleble class, we use for <a href="https://en.wikipedia.org/wiki/Marshalling_(computer_science)" target="_blank" rel="noopener">marshalling/unmarshalling</a> as a more efficient replacement for Serializable. Most common use case is when you want to pass your own class to different activity. Then you need to implement some of it's framework methods

And Bundle itself is Parcelable, it implements it as well.
<h2>Parcel</h2>
When you have your Parcelable implementation you deal with Parcel class. The first thing about this class to understand is how write/read methods work

Basically it's data stored sequentially in order of its insertions, like a list. And you can access any by moving a cursor.
<pre class="lang:java decode:true ">Parcel parcel = Parcel.obtain();

parcel.writeString("hello");
parcel.writeInt(1);
parcel.writeString("world");
parcel.setDataPosition(0);
Log.d(LOG_TAG, "onCreate: "+ parcel.readInt());
Log.d(LOG_TAG, "onCreate: "+ parcel.readString());
Log.d(LOG_TAG, "onCreate: "+ parcel.readString());
parcel.recycle();</pre>
&nbsp;
* */