package com.project.firestoreapplication

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import android.widget.ToggleButton
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.Timestamp
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ListenerRegistration
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.QuerySnapshot
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
import com.project.firestoreapplication.databinding.ContentMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    var selectedCategory = FUNNY
    lateinit var thoughtsAdapter: ThoughtsAdapter
    val thoughts = arrayListOf<Thought>()
    val thoughtsCollectionRef = FirebaseFirestore.getInstance().collection(Constant.THOUGHTS_REF)

    lateinit var thoughtsListener: ListenerRegistration
    lateinit var mainButtonFunny: ToggleButton
    lateinit var mainButtonSerious: ToggleButton
    lateinit var mainButtonCrazy: ToggleButton
    lateinit var mainButtonPopular: ToggleButton
    lateinit var recyclerView: RecyclerView
    lateinit var toolBar: Toolbar
    lateinit var content: ContentMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        content = binding.content
        setContentView(binding.root)
        binding.fab.setOnClickListener { view ->
            val intent = Intent(this, ChatActivity::class.java)
            startActivity(intent)
        }
        thoughtsAdapter = ThoughtsAdapter(thoughts)
        mainButtonCrazy = content.mainCrazyBtn
        mainButtonFunny = content.mainFunnyBtn
        mainButtonPopular = content.mainPopularBtn
        mainButtonSerious = content.mainSeriousBtn
        recyclerView = content.thoughtListView
        recyclerView.adapter = thoughtsAdapter
        val layoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = layoutManager
        clickOnButtons()
        thoughtsCollectionRef.get().addOnSuccessListener { successSnapSort ->
            parseData(successSnapSort)


        }.addOnFailureListener { failureSnapSort ->
            Toast.makeText(this,
                "Error Received!!" + failureSnapSort.localizedMessage,
                Toast.LENGTH_SHORT).show()

        }

    }

    private fun parseData(successSnapSort: QuerySnapshot) {
        thoughts.clear()
        for (document in successSnapSort.documents) {
            val data = document.data
            val userName = data?.get(USERNAME) as String
            val timeStamp = data.get(TIMESTAMP) as Timestamp
            val thoughtText = data?.get(THOUGHT_TXT) as String
            val numLikes = data?.get(NUM_LIKES) as Number
            val numComments = data?.get(NUM_COMMENTS) as Number
            val documentId = document.id
            val thought = Thought(userName,
                timeStamp.toDate(),
                thoughtText,
                numLikes.toInt(),
                numComments.toInt(),
                documentId)
            thoughts.add(thought)
        }
        thoughtsAdapter.notifyDataSetChanged()
    }

    override fun onResume() {
        super.onResume()
        setListener()
    }

    private fun setListener() {
        if (selectedCategory == POPULAR) {
            thoughtsListener = thoughtsCollectionRef.orderBy(NUM_LIKES, Query.Direction.ASCENDING)
                .whereEqualTo(Constant.CATEGORY, selectedCategory)
                .addSnapshotListener(this) {
                        snapshot,
                        exception,
                    ->
                    if (exception != null) {
                        Log.e("Exception", "Could not retrieve the document: $exception")
                    }
                    if (snapshot != null) {
                        parseData(snapshot)

                    }
                }
        } else {
            thoughtsListener = thoughtsCollectionRef.orderBy(TIMESTAMP, Query.Direction.ASCENDING)
                .whereEqualTo(Constant.CATEGORY, selectedCategory)
                .addSnapshotListener(this) {
                        snapshot,
                        exception,
                    ->
                    if (exception != null) {
                        Log.e("Exception", "Could not retrieve the document: $exception")
                    }
                    if (snapshot != null) {
                        parseData(snapshot)

                    }
                }
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
            thoughtsListener.remove()
            setListener()
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
            thoughtsListener.remove()
            setListener()
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
            thoughtsListener.remove()
            setListener()

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
            thoughtsListener.remove()
            setListener()
        }

    }
}