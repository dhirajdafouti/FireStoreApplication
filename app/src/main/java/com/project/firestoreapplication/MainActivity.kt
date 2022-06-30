package com.project.firestoreapplication

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import android.widget.ToggleButton
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ListenerRegistration
import com.project.firestoreapplication.Constant.CRAZY
import com.project.firestoreapplication.Constant.FUNNY
import com.project.firestoreapplication.Constant.NUM_COMMENTS
import com.project.firestoreapplication.Constant.NUM_LIKES
import com.project.firestoreapplication.Constant.POPULAR
import com.project.firestoreapplication.Constant.SERIOUS
import com.project.firestoreapplication.Constant.THOUGHT_TXT
import com.project.firestoreapplication.Constant.TIMESTAMP
import com.project.firestoreapplication.Constant.USERNAME
import com.project.firestoreapplication.databinding.ActivityMainBinding
import com.squareup.okhttp.internal.DiskLruCache
import java.util.*

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    var selectedCategory = FUNNY
    lateinit var thoughtsAdapter: ThoughtsAdapter
    val thoughts = arrayListOf<Thought>()
    val thoughtsCollectionRef = FirebaseFirestore.getInstance().collection(Constant.THOUGHTS_REF)

    //  lateinit var thoughtsListener: ListenerRegistration
    lateinit var mainButtonFunny: ToggleButton
    lateinit var mainButtonSerious: ToggleButton
    lateinit var mainButtonCrazy: ToggleButton
    lateinit var mainButtonPopular: ToggleButton
    lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
        binding.fab.setOnClickListener { view ->
            val intent = Intent(this, ChatActivity::class.java)
            startActivity(intent)
        }
        thoughtsAdapter = ThoughtsAdapter(thoughts)
        mainButtonCrazy = findViewById(R.id.mainCrazyBtn)
        mainButtonFunny = findViewById(R.id.addFunnyBtn)
        mainButtonPopular = findViewById(R.id.mainPopularBtn)
        mainButtonSerious = findViewById(R.id.mainSeriousBtn)
        recyclerView = findViewById(R.id.thoughtListView)
        recyclerView.adapter = thoughtsAdapter
        val layoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = layoutManager
        clickOnButtons()
        thoughtsCollectionRef.get().addOnSuccessListener { successSnapSort ->
            for (document in successSnapSort.documents) {
                val data = document.data
                val userName = data?.get(USERNAME) as String
                val timeStamp = data.get(TIMESTAMP) as Date
                val thoughtText = data.get(THOUGHT_TXT) as String
                val numLikes = data.get(NUM_LIKES) as String
                val numComments = data.get(NUM_COMMENTS) as String
                val documentId = document.id
                val thought = Thought(userName,
                    timeStamp,
                    thoughtText,
                    numLikes.toInt(),
                    numComments.toInt(),
                    documentId)
                thoughts.add(thought)
            }
            thoughtsAdapter.notifyDataSetChanged()


        }.addOnFailureListener { failureSnapSort ->
            Toast.makeText(this,
                "Error Received!!" + failureSnapSort.localizedMessage,
                Toast.LENGTH_SHORT).show()

        }

    }

    private fun clickOnButtons() {
        mainButtonFunny.setOnClickListener {
            if (selectedCategory == FUNNY) {
                mainButtonFunny.isChecked = true
                return@setOnClickListener
            }
            mainButtonSerious.isChecked = false
            mainButtonPopular.isChecked = false
            mainButtonCrazy.isChecked = false
            selectedCategory = FUNNY
            // thoughtsListener.remove()
        }

        mainButtonCrazy.setOnClickListener {
            if (selectedCategory == CRAZY) {
                mainButtonCrazy.isChecked = true
                return@setOnClickListener
            }
            mainButtonFunny.isChecked = false
            mainButtonSerious.isChecked = false
            mainButtonPopular.isChecked = false
            selectedCategory = CRAZY
            //thoughtsListener.remove()
        }

        mainButtonPopular.setOnClickListener {

            if (selectedCategory == POPULAR) {
                mainButtonPopular.isChecked = true
                return@setOnClickListener
            }
            mainButtonFunny.isChecked = false
            mainButtonSerious.isChecked = false
            mainButtonCrazy.isChecked = false
            selectedCategory = POPULAR
            // thoughtsListener.remove()

        }
        mainButtonSerious.setOnClickListener {
            if (selectedCategory == SERIOUS) {
                mainButtonSerious.isChecked = true
                return@setOnClickListener
            }
            mainButtonFunny.isChecked = false
            mainButtonCrazy.isChecked = false
            mainButtonPopular.isChecked = false
            selectedCategory = SERIOUS
            //thoughtsListener.remove()
        }

    }
}