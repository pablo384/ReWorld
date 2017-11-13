package com.reworld.pablo384.reworld.UI.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseArray;
import android.widget.Toast;

import com.google.android.gms.samples.vision.barcodereader.BarcodeCapture;
import com.google.android.gms.samples.vision.barcodereader.BarcodeGraphic;
import com.google.android.gms.vision.barcode.Barcode;
import com.reworld.pablo384.reworld.R;

import java.util.List;
import xyz.belvi.mobilevisionbarcodescanner.BarcodeRetriever;

public class SimpleScannerActivity extends AppCompatActivity implements BarcodeRetriever {

    public static final String TAG = "ScannerActivity.java";
    BarcodeCapture barcodeCapture;
    String preview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simple_scanner);
        barcodeCapture = (BarcodeCapture) getSupportFragmentManager().findFragmentById(R.id.barcode);
        barcodeCapture.setRetrieval(this);

    }

    @Override
    public void onRetrieved(final Barcode barcode) {

        Log.d(TAG, "Barcode read: " + barcode.displayValue);
        if (!barcode.displayValue.equals(preview)){
            preview = barcode.displayValue;
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    final AlertDialog.Builder builder = new AlertDialog.Builder(SimpleScannerActivity.this)
                            .setTitle("code retrieved")
                            .setPositiveButton("Pickup", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    Toast.makeText(SimpleScannerActivity.this,"Added to Pickup List",Toast.LENGTH_LONG).show();
                                    finish();
                                }
                            })
                            .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    finish();
                                }
                            })
                            .setMessage(barcode.displayValue);
                    builder.show();
                }
            });
        }


    }

    @Override
    public void onRetrievedMultiple(final Barcode closetToClick, final List<BarcodeGraphic> barcodeGraphics) {

//        runOnUiThread(new Runnable() {
//            @Override
//            public void run() {
//                String message = "Code selected : " + closetToClick.displayValue + "\n\nother " +
//                        "codes in frame include : \n";
//                for (int index = 0; index < barcodeGraphics.size(); index++) {
//                    Barcode barcode = barcodeGraphics.get(index).getBarcode();
//                    message += (index + 1) + ". " + barcode.displayValue + "\n";
//                }
//                AlertDialog.Builder builder = new AlertDialog.Builder(SimpleScannerActivity.this)
//                        .setTitle("code retrieved")
//                        .setMessage(message);
//                builder.show();
//            }
//        });
    }

    @Override
    public void onBitmapScanned(SparseArray<Barcode> sparseArray) {

    }

    @Override
    public void onRetrievedFailed(String s) {

    }

    @Override
    public void onPermissionRequestDenied() {
        procedApplicationWithoutStory();
    }

    private void procedApplicationWithoutStory() {
        Intent intent = new Intent(SimpleScannerActivity.this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }
}
