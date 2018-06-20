package com.reworld.pablo384.reworld.UI.fragments


import android.Manifest
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.reworld.pablo384.reworld.R
import android.provider.MediaStore
import com.reworld.pablo384.reworld.util.REQUEST_IMAGE_CAPTURE
import kotlinx.android.synthetic.main.fragment_fragment_recycle.view.*
import android.annotation.SuppressLint
import android.app.Activity.RESULT_OK
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.location.Location
import android.net.Uri
import android.os.Environment
import kotlinx.android.synthetic.main.fragment_fragment_recycle.*
import android.support.design.widget.Snackbar
import android.support.v4.content.ContextCompat
import android.support.v4.content.FileProvider
import android.util.Log
import com.ekalips.fancybuttonproj.FancyButton
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.OnProgressListener
import com.google.firebase.storage.UploadTask
import com.karumi.dexter.Dexter
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionDeniedResponse
import com.karumi.dexter.listener.PermissionGrantedResponse
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.single.PermissionListener
import com.reworld.pablo384.reworld.models.Post
import com.reworld.pablo384.reworld.util.FIREBASE_STORAGE_IMAGES
import com.reworld.pablo384.reworld.util.REQUEST_TAKE_PHOTO
import com.reworld.pablo384.reworld.util.USER_LOG
import com.squareup.picasso.Picasso
import me.echodev.resizer.Resizer
import org.jetbrains.anko.support.v4.toast
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*


/**
 * A simple [Fragment] subclass.
 */
class Fragment_recycle : Fragment(),
        GoogleApiClient.OnConnectionFailedListener, GoogleApiClient.ConnectionCallbacks{

    val storage = FirebaseStorage.getInstance()
    var urlDownload:String?=null

    val MY_PERMISSIONS_REQUEST_FINE_LOCATION = 1
    var mlisten:ListenerRecycle?=null
    var mCurrentPhotoPath:String?=null
    var mCurrentPhotoAbsulutePath:String?=null

    var mGoogleApiClient: GoogleApiClient? = null
    var mLastLocation: Location?=null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater!!.inflate(R.layout.fragment_fragment_recycle, container, false)
        with(view){
            buttonTakePicture.setOnClickListener {
                locationPermission()
                if (ContextCompat.checkSelfPermission(context,Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){
                    takePictureIntent()
                }
            }
            buttonUpload.setOnClickListener {
                if (buttonUpload.isExpanded){
                    buttonUpload.collapse()

                    locationPermission()
                    if (ContextCompat.checkSelfPermission(context,Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){

                        if (editTextDescription.text.toString().length > 2){
                            try {
                                uploadImages(editTextDescription.text.toString())
                            }catch (e:Exception){
                                buttonUpload.expand()
                                takePictureIntent()
                                toast("Debes tomar una foto primero")
                            }
                        }else{
                            buttonUpload.expand()
                            toast("The post need a Description")
                        }

                    }else{
                        buttonUpload.expand()
                        toast("I need acces to your location to upload the post")
                    }

                }else{
                    toast("Wait for the process please")
                }


            }

        }

        if (mGoogleApiClient == null) {
            buildGoogleService()
        }

        return view
    }

    private fun uploadImages(description:String){
        val uri = Uri.fromFile(File(mCurrentPhotoAbsulutePath))
        val imagesRef = storage.reference.child(FIREBASE_STORAGE_IMAGES+"/"+uri.lastPathSegment)
        val upload = imagesRef.putFile(uri)
        upload.addOnFailureListener { toast("subida fallo") }.addOnSuccessListener { p0 ->
            urlDownload = p0?.downloadUrl.toString()
            saveData(description)
        }.addOnProgressListener {
            Log.d("TAG", "en proceso de subida")
        }
    }
    private fun saveData(description:String){
        val post = Post(
                USER_LOG?.name,
                description,
                Calendar.getInstance().timeInMillis,mLastLocation!!.latitude,mLastLocation!!.longitude,
                urlDownload.toString())
        val database: FirebaseDatabase = FirebaseDatabase.getInstance()
        val myRef = database.getReference("Post").push()
        myRef.setValue(post)
        buttonUpload.expand()
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)

        try {
            mlisten = context as ListenerRecycle
        }catch (e:Exception){
            e.printStackTrace()
        }
        if (context is ListenerRecycle){
            mlisten = context
        }else{
            throw RuntimeException(context!!.toString() + " must implement ListenerRecycler")
        }

    }

    override fun onStart() {
        mGoogleApiClient?.connect()
        locationPermission()

        super.onStart()

    }

    override fun onStop() {
        mGoogleApiClient?.disconnect()
        super.onStop()
    }

    override fun onResume() {
        super.onResume()
        mlisten?.selectedBottomR(R.id.action_camera)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode === REQUEST_IMAGE_CAPTURE && resultCode === RESULT_OK) {
//            val extras = data?.getExtras()
//            Log.d("TAG",extras.toString())
//            Log.d("TAG",extras?.get("data").toString())
//            val imageBitmap = extras?.get("data") as Bitmap
//            imageViewTookPicture.setImageBitmap(imageBitmap)
            Log.d("TAG", mCurrentPhotoPath.toString())
            Log.d("TAG", mCurrentPhotoAbsulutePath.toString())
            imageViewTookPicture.setPadding(0,0,0,0)
            Picasso.with(activity).load(mCurrentPhotoPath).into(imageViewTookPicture)
            Resizer(context)
                    .setTargetLength(1080)
                    .setQuality(80)
                    .setOutputFormat("JPEG")
                    .setOutputDirPath("/storage/emulated/0/Android/data/com.reworld.pablo384.reworld/files/Pictures/")
                    .setSourceImage(File(mCurrentPhotoAbsulutePath))
                    .resizedFile


        }

    }

    override fun onConnectionFailed(p0: ConnectionResult) {

    }

    @SuppressLint("MissingPermission")
    override fun onConnected(p0: Bundle?) {
        mLastLocation = LocationServices.FusedLocationApi.getLastLocation(
                mGoogleApiClient)
        if (mLastLocation != null) {
//            toast("loc ${mLastLocation!!.latitude} ${mLastLocation!!.longitude}")
        }else{
            locationPermission()
        }
    }

    override fun onConnectionSuspended(p0: Int) {

    }
    private fun locationPermission(){
        Dexter.withActivity(activity)
                .withPermission(Manifest.permission.ACCESS_FINE_LOCATION)
                .withListener(object : PermissionListener {
                    override fun onPermissionGranted(response: PermissionGrantedResponse?) {

                    }

                    override fun onPermissionRationaleShouldBeShown(permission: PermissionRequest?, token: PermissionToken?) {
                        token?.continuePermissionRequest()
                    }

                    override fun onPermissionDenied(response: PermissionDeniedResponse?) {
                        Snackbar.make(myCoordinatorLayoutRecycler,"Necesito el permiso para poder subir las publicaciones",Snackbar.LENGTH_LONG).show()
                    }
                }).check()
    }
    private fun takePictureIntent() {
        val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(context!!.packageManager) != null) {
            // Create the File where the photo should go
            var photoFile:File? = null
            try {
                photoFile = createImageFile()
            } catch (e:IOException) {
                // Error occurred while creating the File
                e.printStackTrace()
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                val photoURI:Uri = FileProvider.getUriForFile(context!!,
                "com.reworld.pablo384.reworld.fileprovider",
                photoFile)
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
                startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO)
            }
        }
    }

    @Throws(IOException::class)
    private fun createImageFile(): File {
        // Create an image file name
        val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        val imageFileName = "JPEG_" + timeStamp + "_"
        val storageDir = context!!.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        val image = File.createTempFile(
                imageFileName, /* prefix */
                ".jpg", /* suffix */
                storageDir      /* directory */
        )

        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = "file:${image.getAbsolutePath()}"
        mCurrentPhotoAbsulutePath = image.absolutePath
//        toast(mCurrentPhotoPath.toString())
        return image
    }
    fun buildGoogleService(){
        mGoogleApiClient = GoogleApiClient.Builder(this.context!!)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build()
    }


fun resizeBitmap(photoPath:String,targetW:Int, targetH:Int):Bitmap {
    val bmOptions: BitmapFactory.Options = BitmapFactory.Options()
    bmOptions.inJustDecodeBounds = true
    BitmapFactory.decodeFile(photoPath, bmOptions);
    val photoW:Int = bmOptions.outWidth;
    val photoH:Int = bmOptions.outHeight;

    var scaleFactor = 1
    if ((targetW > 0) or (targetH > 0)) {
            scaleFactor = Math.min(photoW/targetW, photoH/targetH)
    }

    bmOptions.inJustDecodeBounds = false
    bmOptions.inSampleSize = scaleFactor
    bmOptions.inPurgeable = true //Deprecated API 21

    return BitmapFactory.decodeFile(photoPath, bmOptions)
}



    interface ListenerRecycle{
        fun selectedBottomR(name:Int)
    }



}// Required empty public constructor
