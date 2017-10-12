package com.reworld.pablo384.reworld.UI.fragments


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
import android.graphics.Bitmap
import android.R.attr.data
import android.support.v4.app.NotificationCompat.getExtras
import android.app.Activity.RESULT_OK
import android.net.Uri
import android.os.Environment
import kotlinx.android.synthetic.main.fragment_fragment_recycle.*
import android.os.Environment.DIRECTORY_PICTURES
import android.support.v4.content.FileProvider
import android.util.Log
import com.reworld.pablo384.reworld.util.REQUEST_TAKE_PHOTO
import com.squareup.picasso.Picasso
import org.jetbrains.anko.support.v4.toast
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*


/**
 * A simple [Fragment] subclass.
 */
class Fragment_recycle : Fragment() {


    var mlisten:ListenerRecycle?=null
    var mCurrentPhotoPath:String?=null
    var mCurrentPhotoAbsulutePath:String?=null

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater!!.inflate(R.layout.fragment_fragment_recycle, container, false)
        with(view){
            buttonTakePicture.setOnClickListener { takePictureIntent() }
        }
        return view
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
            Picasso.with(activity).load(mCurrentPhotoPath).into(imageViewTookPicture)
        }

    }

//    private fun takePictureIntent() {
//        val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
//        if (takePictureIntent.resolveActivity(context.packageManager) != null) {
//            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE)
//        }
//    }
    private fun takePictureIntent() {
        val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(context.packageManager) != null) {
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
                val photoURI:Uri = FileProvider.getUriForFile(context,
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
        val storageDir = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        val image = File.createTempFile(
                imageFileName, /* prefix */
                ".jpg", /* suffix */
                storageDir      /* directory */
        )

        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = "file:${image.getAbsolutePath()}"
        mCurrentPhotoAbsulutePath = image.absolutePath
        toast(mCurrentPhotoPath.toString())
        return image
    }

    interface ListenerRecycle{
        fun selectedBottomR(name:Int)
    }



}// Required empty public constructor
