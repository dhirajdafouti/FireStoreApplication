package com.project.realdb

import android.content.ContentResolver
import android.net.Uri
import android.os.Bundle
import android.webkit.MimeTypeMap
import android.widget.*
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.project.realdb.model.Upload
import com.squareup.picasso.Picasso

class RealDbActivity : AppCompatActivity() {

    //https://www.youtube.com/watch?v=wa8OrQ_e76M&list=PL5jb9EteFAOCO_uRl2--aQ-0d8r01QjS-
    //https://www.youtube.com/watch?v=oPH7rfJel9w
    //https://youtube.com/playlist?list=PLl-K7zZEsYLmgdxMEHar35Wo26fLWm9BI
    //https://youtube.com/playlist?list=PLQ9S01mirRdXlj1xr2fn0NjUwa9HWNPfm
    //https://youtube.com/playlist?list=PLrnPJCHvNZuAXdWxOzsN5rgG2M4uJ8bH1
    //https://youtube.com/playlist?list=PLj76U7gxVixR0ZDpZIpDG4mDo7fqIR2ec
    //https://youtube.com/playlist?list=PLj76U7gxVixQ1JcaOCA7U12SoS8l-21xU
    //https://youtube.com/playlist?list=PLhhNsarqV6MQ-eMvAOwjuBUDm7hfsTUta
    //https://youtube.com/playlist?list=PLrnPJCHvNZuB_7nB5QD-4bNg6tpdEUImQ
    //https://firebase.google.com/docs/auth/?hl=en&authuser=0
    //https://www.youtube.com/watch?v=hXuI0nLWKTE&list=PLYx38U7gxBf3pmsHVTUwRT_lGON6ZIBHi&index=5
    //https://www.youtube.com/playlist?list=PLYx38U7gxBf3pmsHVTUwRT_lGON6ZIBHi
    //https://firebase.google.com/support/guides/launch-checklist?authuser=0
    //https://www.youtube.com/watch?v=HQgJvHXsNOQ&list=PLrnPJCHvNZuDrSqu-dKdDi3Q6nM-VUyxD&index=14
    //https://www.youtube.com/watch?v=MfCiiTEwt3g&list=PLrnPJCHvNZuB_7nB5QD-4bNg6tpdEUImQ&index=1(Best )

    ///////////////////////////////////////////////////////////////////////////////
    //https://www.youtube.com/watch?v=4ArxBP5DSZc&list=RDCMUCmL5TAblHHgh1xhabmPjYgw&start_radio=1&t=7s
    //https://www.youtube.com/playlist?list=PLesEwfOYAU4aeBtbu5EfEdtsLozsQT14d
    //https://www.youtube.com/playlist?list=PL5jb9EteFAODQIS3hMKb5e8EwAq3LIOKx
    //https://www.youtube.com/playlist?list=PLirRGafa75rQOi3so_ngAHqDmq_Djifwu(best)
    //https://www.youtube.com/playlist?list=PLesEwfOYAU4YsS038rYtf5AEQ1KXwFEA_
    //https://www.youtube.com/watch?v=4ArxBP5DSZc&list=RDCMUCmL5TAblHHgh1xhabmPjYgw&start_radio=1&t=7s(best)
    //https://www.youtube.com/watch?v=b1bGrWrx5Mo&list=PLUhfM8afLE_P3pFFUZQLGdWKOcU5cl-Xz(best)
    //https://www.youtube.com/c/CodeWithMazn(best)
    //https://www.youtube.com/watch?v=eGWu0-0TWFI(best)
    //https://www.youtube.com/watch?v=pTAueJvG77k&list=PLgCYzUzKIBE_cyEsXgIcwC3P8ipvlSFd_(best)
    //https://www.youtube.com/watch?v=eGWu0-0TWFI(best)

    private lateinit var mButtonChooseImage: Button
    private lateinit var mButtonUpload: Button
    private lateinit var mTextViewShosUploads: TextView
    private lateinit var mEdittextFileName: EditText
    private lateinit var mImageView: ImageView
    private lateinit var progressBar: ProgressBar
    private lateinit var mStorage: StorageReference
    private lateinit var mDatabaseRef: DatabaseReference
    private lateinit var imageUri: Uri
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_real_db)
        mButtonChooseImage = findViewById(R.id.button_choose_image)
        mButtonUpload = findViewById(R.id.button_upload)
        mButtonUpload.isEnabled = false
        mTextViewShosUploads = findViewById(R.id.text_view_show_uploads)
        mEdittextFileName = findViewById(R.id.edit_text_file_name)
        mImageView = findViewById(R.id.image_view)
        progressBar = findViewById(R.id.progress_bar)
        mStorage = FirebaseStorage.getInstance().getReference("Uploads")
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("Uploads")
        auth = Firebase.auth
        mButtonChooseImage.setOnClickListener {
            reload()
        }

        mButtonUpload.setOnClickListener {
            uploadFile()
        }
        mTextViewShosUploads.setOnClickListener {

        }
    }


    private fun reload() {
        auth.createUserWithEmailAndPassword("dhirajdafouti2020@gmail.com".trim(),
            "Dhiraj@221115".trim()).addOnSuccessListener {
            mButtonUpload.isEnabled = true
            openFileChooser()
        }.addOnFailureListener {
            runOnUiThread {
                Toast.makeText(this, "Login Failed", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun uploadFile() {
        val stringBuilder: StringBuilder = StringBuilder()
        val value = stringBuilder.append(System.currentTimeMillis()).append(".")
            .append(getFileExtension(imageUri))
        val fileReference: StorageReference = mStorage.child(value.toString())
        fileReference.putFile(imageUri).addOnSuccessListener {
            runOnUiThread {
                progressBar.progress = 0
            }
            val uploadDetails =
                Upload(mEdittextFileName.text.toString().trim(), it.uploadSessionUri.toString())
            val uploadId: String? = mDatabaseRef.push().key
            uploadId?.let { upload ->
                mDatabaseRef.child(upload).setValue(mEdittextFileName.text.toString()
                    .trim() + it.uploadSessionUri.toString())
            }
        }.addOnFailureListener {
            Toast.makeText(this,
                "Exception:Uploading Failed!!" + it.localizedMessage,
                Toast.LENGTH_SHORT).show()
        }.addOnProgressListener {
            runOnUiThread {
                val progress: Long = (100 * it.bytesTransferred / it.totalByteCount)
                progressBar.progress = progress.toInt()
            }

        }
    }

    private fun getFileExtension(uri: Uri): String? {
        val contentResolver: ContentResolver = contentResolver
        val map: MimeTypeMap = MimeTypeMap.getSingleton()
        return map.getMimeTypeFromExtension(contentResolver.getType(uri))
    }

    private fun openFileChooser() {
        startForResult.launch("image/*")
    }

    private val startForResult =
        registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
            if (uri != null) {
                imageUri = uri
            }
            Picasso.with(this).load(uri).into(mImageView)
        }
}